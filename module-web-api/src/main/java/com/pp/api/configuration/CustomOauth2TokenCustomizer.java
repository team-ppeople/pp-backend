package com.pp.api.configuration;

import com.pp.api.entity.OauthUsers;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.service.OauthUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.PRIVATE_KEY_JWT;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.SERVER_ERROR;
import static org.springframework.security.oauth2.jwt.JwtClaimNames.*;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;

@Component
@RequiredArgsConstructor
public final class CustomOauth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final OauthUsersService oauthUsersService;

    @Override
    public void customize(JwtEncodingContext context) {
        try {
            JwtClaimsSet.Builder builder = context.getClaims();

            if (context.getTokenType().equals(ACCESS_TOKEN)) {
                if (
                        context.getPrincipal() instanceof OAuth2ClientAuthenticationToken authentication &&
                                authentication.getClientAuthenticationMethod() == PRIVATE_KEY_JWT &&
                                authentication.getCredentials() instanceof Jwt jwt
                ) {
                    String subject = jwt.getSubject();

                    String clientName = authentication.getRegisteredClient()
                            .getClientName();

                    String clientSubject = OauthUserClient.valueOfIgnoreCase(clientName)
                            .parseClientSubject(subject);

                    OauthUsers oauthUser = oauthUsersService.findByClientSubject(clientSubject);

                    builder.claims(claims -> {
                        claims.put(SUB, oauthUser.getUser().getId());
                        claims.put(AUD, claims.get(ISS));
                    });
                }
            }
        } catch (Exception e) {
            throw new OAuth2AuthenticationException(SERVER_ERROR);
        }
    }

}
