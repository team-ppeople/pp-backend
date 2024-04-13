package com.pp.api.event;

import com.pp.api.client.AppleClient;
import com.pp.api.client.dto.AppleTokenResponse;
import com.pp.api.entity.OauthUserTokens;
import com.pp.api.event.handler.OauthUserRegisteredEvent;
import com.pp.api.repository.OauthUserTokensRepository;
import com.pp.api.repository.OauthUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.pp.api.entity.enums.OauthUserClient.APPLE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.SERVER_ERROR;

@Component
@RequiredArgsConstructor
public class OauthUserRegisteredEventHandler {

    private final AppleClient appleClient;

    private final OauthUserTokensRepository oauthUserTokensRepository;

    private final OauthUsersRepository oauthUsersRepository;

    @EventListener(value = OauthUserRegisteredEvent.class)
    public void handle(OauthUserRegisteredEvent event) {
        if (event.getOauthUserClient() != APPLE) {
            return;
        }

        AppleTokenResponse token;

        try {
            token = appleClient.token(event.getAuthorizationCode());
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new OAuth2AuthenticationException(INVALID_REQUEST);
            }

            throw e;
        }

        OauthUserTokens oauthUserToken = OauthUserTokens.builder()
                .client(event.getOauthUserClient())
                .accessToken(token.getAccessToken())
                .expiresIn(token.getExpiresIn())
                .refreshToken(token.getRefreshToken())
                .oauthUser(oauthUsersRepository.getReferenceById(event.getOauthUserId()))
                .build();

        oauthUserTokensRepository.save(oauthUserToken);
    }

}
