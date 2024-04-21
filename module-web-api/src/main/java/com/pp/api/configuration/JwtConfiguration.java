package com.pp.api.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.pp.api.configuration.properties.Oauth2KeyProperties;
import com.pp.api.configuration.properties.Oauth2KeyProperties.Oauth2Key;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.from;

@Configuration
@EnableConfigurationProperties(value = Oauth2KeyProperties.class)
@RequiredArgsConstructor
public class JwtConfiguration {

    private final Oauth2KeyProperties oauth2KeyProperties;

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet jwkSet = new JWKSet(createJwks());

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private List<JWK> createJwks() {
        return oauth2KeyProperties.jwk()
                .stream()
                .map(this::createJwk)
                .toList();
    }

    private JWK createJwk(Oauth2Key key) {
        switch (from(key.type())) {
            case RS256, RS384, RS512 -> {
                return new RSAKey
                        .Builder((RSAPublicKey) createPublicKey(key.publicKey()))
                        .privateKey((RSAPrivateKey) createPrivateKey(key.privateKey()))
                        .keyID(key.id())
                        .build();
            }
            default -> throw new UnsupportedOperationException("Unsupported key type: " + key.type());
        }
    }

    private PublicKey createPublicKey(String encodedPublicKey) {
        byte[] decode = Base64.getDecoder()
                .decode(encodedPublicKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(new X509EncodedKeySpec(decode));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private PrivateKey createPrivateKey(String encodedPrivateKey) {
        byte[] decode = Base64.getDecoder()
                .decode(encodedPrivateKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decode));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
