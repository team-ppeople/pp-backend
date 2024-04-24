package com.pp.api.configuration;

import com.pp.api.client.AppleClient;
import com.pp.api.client.dto.AppleTokenResponse;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.service.OauthUserTokensService;
import com.pp.api.service.OauthUserService;
import com.pp.api.service.command.RegisterOauthUserCommand;
import com.pp.api.service.command.SaveOauthUserTokenCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.pp.api.entity.enums.OauthUserClient.APPLE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.EMAIL;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class AppleJwtClientAssertionOauth2UserRegisterProcessor implements JwtClientAssertionOauth2UserRegisterProcessor {

    private final OauthUserService oauthUserService;

    private final OauthUserTokensService oauthUserTokensService;

    private final AppleClient appleClient;

    @Override
    public void process(JwtClientAssertionOauth2ClientCredentialsAuthenticationToken authentication) {
        OAuth2ClientAuthenticationToken clientAuthentication = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();

        RegisteredClient registeredClient = clientAuthentication.getRegisteredClient();

        if (registeredClient == null) {
            throw new OAuth2AuthenticationException(INVALID_REQUEST);
        }

        OauthUserClient client = OauthUserClient.valueOfIgnoreCase(registeredClient.getClientName());

        Jwt jwt = (Jwt) clientAuthentication.getCredentials();

        if (jwt == null) {
            throw new OAuth2AuthenticationException(INVALID_REQUEST);
        }

        String authorizationCode = (String) authentication.getAdditionalParameters()
                .get(AUTHORIZATION_CODE.getValue());

        if (!hasText(authorizationCode)) {
            throw new OAuth2AuthenticationException(INVALID_REQUEST);
        }

        AppleTokenResponse token;

        try {
            token = appleClient.token(authorizationCode);
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new OAuth2AuthenticationException(INVALID_REQUEST);
            }

            throw e;
        }

        String subject = jwt.getSubject();

        String email = jwt.getClaimAsString(EMAIL);

        String nickname = "유저" + subject.substring(
                0,
                subject.indexOf(".")
        );

        SaveOauthUserTokenCommand saveOauthUserTokenCommand = SaveOauthUserTokenCommand.of(
                client,
                subject,
                token.getAccessToken(),
                token.getExpiresIn(),
                token.getRefreshToken()
        );

        RegisterOauthUserCommand registerOauthUserCommand = RegisterOauthUserCommand.of(
                client,
                subject,
                nickname,
                email
        );

        oauthUserService.registerIfNotRegistered(registerOauthUserCommand);
        oauthUserTokensService.save(saveOauthUserTokenCommand);
    }

    @Override
    public boolean support(OauthUserClient client) {
        return client == APPLE;
    }

}
