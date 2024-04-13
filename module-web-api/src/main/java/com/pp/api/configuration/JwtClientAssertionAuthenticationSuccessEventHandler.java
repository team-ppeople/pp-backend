package com.pp.api.configuration;

import com.pp.api.client.AppleClient;
import com.pp.api.client.dto.AppleTokenResponse;
import com.pp.api.entity.OauthUserTokens;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.repository.OauthUserTokensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

import static com.pp.api.entity.enums.OauthUserClient.APPLE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.PRIVATE_KEY_JWT;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.SERVER_ERROR;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtClientAssertionAuthenticationSuccessEventHandler {

    private final AppleClient appleClient;

    private final OauthUserTokensRepository oauthUserTokensRepository;

    @EventListener(value = AuthenticationSuccessEvent.class)
    public void handle(AuthenticationSuccessEvent event) {
        if (
                event.getSource() instanceof OAuth2AccessTokenAuthenticationToken authentication &&
                        authentication.getPrincipal() instanceof OAuth2ClientAuthenticationToken clientAuthentication &&
                        clientAuthentication.getClientAuthenticationMethod().equals(PRIVATE_KEY_JWT) &&
                        clientAuthentication.getCredentials() instanceof Jwt token
        ) {
            signUp(token);
            exchangeToken(authentication);
        }
    }

    private void signUp(Jwt jwt) {
        // TODO idp 토큰 페이로드에 따라 이미 등록되지 않았다면 유저 등록 처리
        Map<String, Object> claims = jwt.getClaims();
    }

    private void exchangeToken(OAuth2AccessTokenAuthenticationToken authentication) {
        String clientName = authentication.getRegisteredClient()
                .getClientName();

        OauthUserClient client = OauthUserClient.valueOfIgnoreCase(clientName);

        if (client != APPLE) {
            return;
        }

        String authorizationCode = (String) authentication.getAdditionalParameters()
                .get(AUTHORIZATION_CODE.getValue());

        if (!hasText(authorizationCode)) {
            throw new OAuth2AuthenticationException(INVALID_REQUEST);
        }

        AppleTokenResponse token;

        try {
            token = appleClient.token(authorizationCode);
        } catch (WebClientResponseException e) { // TODO 에러 처리
            throw new OAuth2AuthenticationException(INVALID_REQUEST);
        } catch (WebClientRequestException e) {  // TODO 에러 처리
            throw new OAuth2AuthenticationException(SERVER_ERROR);
        }

        OauthUserTokens oauthUserToken = OauthUserTokens.builder()
                .client(client)
                .accessToken(token.accessToken())
                .expiresIn(token.expiresIn())
                .refreshToken(token.refreshToken())
                .build();

        oauthUserTokensRepository.save(oauthUserToken);
    }

}
