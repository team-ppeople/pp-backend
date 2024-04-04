package com.pp.api.configuration;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.SERVER_ERROR;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;

@Component
public final class CustomOauth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {
        try {
            JwtClaimsSet.Builder claims = context.getClaims();

            if (context.getTokenType().equals(ACCESS_TOKEN)) {
                // TODO 유저 정보를 조회해서 custom claims 설정
                claims.claim("user_id", 1L);

                return;
            }
        } catch (Exception e) {
            throw new OAuth2AuthenticationException(SERVER_ERROR);
        }
    }

}
