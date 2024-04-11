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
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.nimbusds.jose.jwk.Curve.P_256;

@Disabled
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(value = "test")
public abstract class AbstractIntegrationTestContext {

    protected MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerDynamicProperty(DynamicPropertyRegistry registry) {
        registry.add(
                "client.apple.private-key",
                AbstractIntegrationTestContext::generateES256PrivateKey
        );
    }

    static String generateES256PrivateKey() {
        KeyPairGenerator keyPairGenerator;

        try {
            keyPairGenerator = KeyPairGenerator.getInstance("EC");

            keyPairGenerator.initialize(P_256.toECParameterSpec());
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        byte[] encoded = keyPairGenerator.generateKeyPair()
                .getPrivate()
                .getEncoded();

        return Base64.getEncoder()
                .encodeToString(encoded);
    }

}
