package com.pp.api.configuration;

import com.pp.api.entity.enums.OauthUserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;

@Primary
@Component
@RequiredArgsConstructor
public class CompositeJwtClientAssertionOauth2UserRegisterProcessor implements JwtClientAssertionOauth2UserRegisterProcessor {

    private final List<JwtClientAssertionOauth2UserRegisterProcessor> composites;

    @Override
    public void process(JwtClientAssertionOauth2ClientCredentialsAuthenticationToken authentication) {
        OAuth2ClientAuthenticationToken clientAuthentication = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();

        RegisteredClient registeredClient = clientAuthentication.getRegisteredClient();

        if (registeredClient == null) {
            throw new OAuth2AuthenticationException(INVALID_REQUEST);
        }

        OauthUserClient client = OauthUserClient.valueOfIgnoreCase(registeredClient.getClientName());

        for (JwtClientAssertionOauth2UserRegisterProcessor register : composites) {
            if (register.support(client)) {
                register.process(authentication);

                return;
            }
        }

        throw new OAuth2AuthenticationException(INVALID_REQUEST);
    }

    @Override
    public boolean support(OauthUserClient client) {
        return true;
    }

}
