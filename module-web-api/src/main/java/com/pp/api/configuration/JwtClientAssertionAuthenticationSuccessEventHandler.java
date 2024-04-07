package com.pp.api.configuration;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.PRIVATE_KEY_JWT;

@Component
public class JwtClientAssertionAuthenticationSuccessEventHandler {

    @EventListener(value = AuthenticationSuccessEvent.class)
    public void handle(AuthenticationSuccessEvent event) {
        if (
                event.getSource() instanceof OAuth2ClientAuthenticationToken token &&
                        token.getClientAuthenticationMethod().equals(PRIVATE_KEY_JWT) &&
                        token.getCredentials() instanceof Jwt jwt
        ) {
            signUp(jwt);
        }
    }

    private void signUp(Jwt jwt) {
        // TODO idp 토큰 페이로드에 따라 이미 등록되지 않았다면 유저 등록 처리
        Map<String, Object> claims = jwt.getClaims();
    }

}
