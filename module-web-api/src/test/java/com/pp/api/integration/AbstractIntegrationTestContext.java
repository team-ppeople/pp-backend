package com.pp.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import static com.nimbusds.jose.jwk.Curve.P_256;
import static com.nimbusds.jose.jwk.KeyType.RSA;
import static java.security.KeyPairGenerator.getInstance;
import static java.util.UUID.randomUUID;

@Disabled
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(value = "test")
public abstract class AbstractIntegrationTestContext {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerDynamicProperty(DynamicPropertyRegistry registry) {
        registerOauth2JwkProperty(registry);
        registerApplePrivateKeyProperty(registry);
    }

    static void registerOauth2JwkProperty(DynamicPropertyRegistry registry) {
        KeyPairGenerator keyPairGenerator;

        try {
            keyPairGenerator = getInstance("RSA");

            keyPairGenerator.initialize(2048);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        registry.add(
                "oauth2.jwk.id",
                () -> randomUUID().toString()
        );

        registry.add(
                "oauth2.jwk.type",
                RSA::getValue
        );

        registry.add(
                "oauth2.jwk.public-key",
                () -> Base64.getEncoder()
                        .encodeToString(publicKey.getEncoded())
        );

        registry.add(
                "oauth2.jwk.private-key",
                () -> Base64.getEncoder()
                        .encodeToString(privateKey.getEncoded())
        );
    }

    static void registerApplePrivateKeyProperty(DynamicPropertyRegistry registry) {
        KeyPairGenerator keyPairGenerator;

        try {
            keyPairGenerator = KeyPairGenerator.getInstance("EC");

            keyPairGenerator.initialize(P_256.toECParameterSpec());
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        registry.add(
                "client.apple.private-key",
                () -> Base64.getEncoder()
                        .encodeToString(keyPair.getPrivate().getEncoded())
        );
    }

}
