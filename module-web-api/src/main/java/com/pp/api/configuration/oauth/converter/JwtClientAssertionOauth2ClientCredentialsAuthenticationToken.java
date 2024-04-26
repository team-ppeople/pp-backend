package com.pp.api.configuration.oauth.converter;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;

@Getter
public final class JwtClientAssertionOauth2ClientCredentialsAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final Set<String> scopes;

    public JwtClientAssertionOauth2ClientCredentialsAuthenticationToken(
            Authentication clientPrincipal,
            Set<String> scopes,
            Map<String, Object> additionalParameters
    ) {
        super(
                CLIENT_CREDENTIALS,
                clientPrincipal,
                additionalParameters
        );

        this.scopes = unmodifiableSet(scopes);
    }

}
