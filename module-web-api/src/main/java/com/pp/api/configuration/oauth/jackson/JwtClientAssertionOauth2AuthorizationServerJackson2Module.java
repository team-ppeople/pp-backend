package com.pp.api.configuration.oauth.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import static org.springframework.security.jackson2.SecurityJackson2Modules.enableDefaultTyping;

/**
 * @see <a href="https://github.com/spring-projects/spring-security/issues/4370">spring authorization server github issue</a>
 */
public class JwtClientAssertionOauth2AuthorizationServerJackson2Module extends SimpleModule {

    public JwtClientAssertionOauth2AuthorizationServerJackson2Module() {
        super(
                JwtClientAssertionOauth2AuthorizationServerJackson2Module.class.getName(),
                new Version(
                        1,
                        0,
                        0,
                        null,
                        null,
                        null
                )
        );
    }

    public void setupModule(SetupContext context) {
        enableDefaultTyping(context.getOwner());

        context.setMixInAnnotations(OAuth2ClientAuthenticationToken.class, Oauth2ClientAuthenticationTokenMixIn.class);
        context.setMixInAnnotations(ClientAuthenticationMethod.class, ClientAuthenticationMethodMixIn.class);
        context.setMixInAnnotations(Jwt.class, JwtMixIn.class);
        context.setMixInAnnotations(Long.class, LongMixIn.class);
        context.setMixInAnnotations(RegisteredClient.class, RegisteredClientMixIn.class);
        context.setMixInAnnotations(AuthorizationGrantType.class, AuthorizationGrantTypeMixIn.class);
        context.setMixInAnnotations(ClientSettings.class, ClientSettingsMixIn.class);
        context.setMixInAnnotations(TokenSettings.class, TokenSettingsMixIn.class);
    }

}
