package com.pp.api.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.api.configuration.properties.AppleClientProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.jsonwebtoken.Jwts.SIG.ES256;
import static java.time.ZoneId.systemDefault;
import static org.springframework.security.oauth2.jwt.JwtClaimNames.EXP;

@Component
public class AppleClientSecretGenerator {

    private static final Map<String, String> CLIENT_SECRETS_CACHE = new ConcurrentHashMap<>();

    private final PrivateKey privateKey;

    private final AppleClientProperties appleClientProperties;

    private final ObjectMapper objectMapper;

    public AppleClientSecretGenerator(
            AppleClientProperties appleClientProperties,
            ObjectMapper objectMapper
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        byte[] decode = Base64.getDecoder()
                .decode(appleClientProperties.privateKey());

        this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decode));
        this.appleClientProperties = appleClientProperties;
        this.objectMapper = objectMapper;
    }

    public String getOrGenerate() {
        String clientSecret = CLIENT_SECRETS_CACHE.get(appleClientProperties.clientSecretKid());

        if (!StringUtils.hasText(clientSecret)) {
            return generateAndCache();
        }

        if (!isAlive(clientSecret)) {
            return generateAndCache();
        }

        return clientSecret;
    }

    private boolean isAlive(String clientSecret) {
        if (!StringUtils.hasText(clientSecret)) {
            return false;
        }

        byte[] decode = Base64.getDecoder()
                .decode(clientSecret.split("\\.")[1]);

        Map<String, Object> claims;

        try {
            claims = objectMapper.readValue(decode, new TypeReference<>() {
            });
        } catch (IOException e) {
            return false;
        }

        LocalDateTime expiration = LocalDateTime.ofInstant(
                Instant.ofEpochSecond((Integer) claims.get(EXP)),
                systemDefault()
        );

        return LocalDateTime.now()
                .plusHours(1)
                .isBefore(expiration);
    }

    private String generateAndCache() {
        String clientSecret = Jwts.builder()
                .header()
                .keyId(appleClientProperties.clientSecretKid())
                .and()
                .issuer(appleClientProperties.clientSecretIssuer())
                .subject(appleClientProperties.clientId())
                .issuedAt(new Date())
                .expiration(createExpiration())
                .audience()
                .add(appleClientProperties.baseUrl())
                .and()
                .signWith(
                        privateKey,
                        ES256
                )
                .compact();

        CLIENT_SECRETS_CACHE.put(
                appleClientProperties.clientSecretKid(),
                clientSecret
        );

        return clientSecret;
    }

    private Date createExpiration() {
        Instant instant = LocalDateTime.now()
                .plusDays(appleClientProperties.clientSecretExpirationDays())
                .atZone(systemDefault())
                .toInstant();

        return Date.from(instant);
    }

}
