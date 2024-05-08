package com.pp.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.api.util.DatabaseCleanUtil;
import com.pp.api.util.JwtTestUtil;
import com.pp.api.util.LocalStackSetUpUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import static com.nimbusds.jose.jwk.Curve.P_256;
import static java.security.KeyPairGenerator.getInstance;
import static java.util.UUID.randomUUID;
import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.RS256;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Disabled
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(value = "test")
public abstract class AbstractIntegrationTestContext {

    private static final LocalStackContainer LOCAL_STACK_CONTAINER;

    private static final GenericContainer<?> REDIS_CONTAINER;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtTestUtil jwtTestUtil;

    @Autowired
    private DatabaseCleanUtil databaseCleanUtil;

    static {
        REDIS_CONTAINER = new GenericContainer<>(DockerImageName.parse("redis:7.0"))
                .withExposedPorts(6379);

        REDIS_CONTAINER.start();

        LOCAL_STACK_CONTAINER = new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.4"))
                .withServices(S3);

        LOCAL_STACK_CONTAINER.start();
    }

    @BeforeAll
    static void setUp(@Autowired LocalStackSetUpUtil localStackSetUpUtil) {
        localStackSetUpUtil.setUp();
    }

    @AfterEach
    void tearDown() {
        databaseCleanUtil.clear();
    }

    @DynamicPropertySource
    static void registerDynamicProperty(DynamicPropertyRegistry registry) {
        registerRedisProperty(registry);
        registerOauth2JwkProperty(registry);
        registerApplePrivateKeyProperty(registry);
        registerLocalStackProperty(registry);
    }

    static void registerRedisProperty(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.redis.port",
                () -> REDIS_CONTAINER.getFirstMappedPort() + ""
        );
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
                "oauth2.jwk[0].id",
                () -> randomUUID().toString()
        );

        registry.add(
                "oauth2.jwk[0].type",
                RS256::getName
        );

        registry.add(
                "oauth2.jwk[0].public-key",
                () -> Base64.getEncoder()
                        .encodeToString(publicKey.getEncoded())
        );

        registry.add(
                "oauth2.jwk[0].private-key",
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

    static void registerLocalStackProperty(DynamicPropertyRegistry registry) {
        registry.add(
                "aws.s3.access-key",
                LOCAL_STACK_CONTAINER::getAccessKey
        );

        registry.add(
                "aws.s3.secret-key",
                LOCAL_STACK_CONTAINER::getSecretKey
        );

        registry.add(
                "aws.s3.region",
                LOCAL_STACK_CONTAINER::getRegion
        );

        registry.add(
                "aws.s3.endpoint-override-uri",
                LOCAL_STACK_CONTAINER::getEndpoint
        );
    }

}
