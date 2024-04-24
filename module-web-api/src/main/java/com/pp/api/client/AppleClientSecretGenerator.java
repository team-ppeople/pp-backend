package com.pp.api.client;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.pp.api.configuration.property.AppleClientProperty;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.nimbusds.jose.JWSAlgorithm.ES256;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.springframework.util.StringUtils.hasText;

@Component
public class AppleClientSecretGenerator {

    private static final Map<String, String> CLIENT_SECRETS_CACHE = new ConcurrentHashMap<>();

    private final JWSSigner jwsSigner;

    private final AppleClientProperty appleClientProperty;

    public AppleClientSecretGenerator(AppleClientProperty appleClientProperty)
            throws InvalidKeySpecException, JOSEException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        byte[] decode = Base64.getDecoder()
                .decode(appleClientProperty.privateKey());

        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decode));

        this.jwsSigner = new ECDSASigner((ECPrivateKey) privateKey);
        this.appleClientProperty = appleClientProperty;
    }

    public String getOrGenerate() {
        String clientSecret = CLIENT_SECRETS_CACHE.get(appleClientProperty.clientSecretKid());

        if (!hasText(clientSecret)) {
            return generateAndCache();
        }

        if (!isAlive(clientSecret)) {
            return generateAndCache();
        }

        return clientSecret;
    }

    private boolean isAlive(String clientSecret) {
        if (!hasText(clientSecret)) {
            return false;
        }

        Date expiration;

        try {
            expiration = SignedJWT.parse(clientSecret)
                    .getJWTClaimsSet()
                    .getExpirationTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return createAliveCheckDate().before(expiration);
    }

    private String generateAndCache() {
        JWSHeader jwsHeader = new JWSHeader.Builder(ES256)
                .keyID(appleClientProperty.clientSecretKid())
                .build();

        Date now = new Date();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer(appleClientProperty.clientSecretIssuer())
                .subject(appleClientProperty.clientId())
                .issueTime(now)
                .expirationTime(createExpiration(now))
                .audience(appleClientProperty.baseUrl())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                jwsHeader,
                jwtClaimsSet
        );

        try {
            signedJWT.sign(jwsSigner);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        String clientSecret = signedJWT.serialize();

        CLIENT_SECRETS_CACHE.put(
                appleClientProperty.clientSecretKid(),
                clientSecret
        );

        return clientSecret;
    }

    private Date createExpiration(Date date) {
        Instant instant = date.toInstant()
                .plus(
                        appleClientProperty.clientSecretExpirationDays(),
                        DAYS
                );

        return Date.from(instant);
    }

    private Date createAliveCheckDate() {
        Date now = new Date();

        Instant instant = now.toInstant()
                .plus(1, HOURS);

        return Date.from(instant);
    }

}
