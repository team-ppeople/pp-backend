package com.pp.api.configuration.oauth.processor;

import com.pp.api.configuration.oauth.converter.JwtClientAssertionOauth2ClientCredentialsAuthenticationToken;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.service.OauthUserService;
import com.pp.api.service.command.RegisterOauthUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import static com.pp.api.entity.enums.OauthUserClient.KAKAO;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.EMAIL;

@Component
@RequiredArgsConstructor
public class KakaoJwtClientAssertionOauth2UserRegisterProcessor implements JwtClientAssertionOauth2UserRegisterProcessor {

    private final OauthUserService oauthUserService;

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

        String subject = jwt.getSubject();

        String nickname = "유저" + subject;

        String email = jwt.getClaimAsString(EMAIL);

        RegisterOauthUserCommand command = new RegisterOauthUserCommand(
                client,
                subject,
                nickname,
                email
        );

        oauthUserService.registerIfNotRegistered(command);
    }

    @Override
    public boolean support(OauthUserClient client) {
        return client == KAKAO;
    }

}
