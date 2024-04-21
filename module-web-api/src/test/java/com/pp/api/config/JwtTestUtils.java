package com.pp.api.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.pp.api.configuration.properties.Oauth2KeyProperties;
import com.pp.api.configuration.properties.Oauth2KeyProperties.Oauth2Key;
import com.pp.api.entity.Users;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.UUID.randomUUID;
import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.from;

@Component
public class JwtTestUtils {

    private static final Set<String> ALLOWED_SCOPES = Set.of(
            "user.read",
            "user.write",
            "post.read",
            "post.write",
            "file.write"
    );

    private final JwtEncoder jwtEncoder;

    private final Oauth2KeyProperties oauth2KeyProperties;

    public JwtTestUtils(
            JWKSource<SecurityContext> jwkSource,
            Oauth2KeyProperties oauth2KeyProperties
    ) {
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);
        this.oauth2KeyProperties = oauth2KeyProperties;
    }

    public String createBearerToken(Users users) {
        return createBearerToken(String.valueOf(users.getId()));
    }

    public String createBearerToken(String subject) {
        return "Bearer " + createJwt(subject).getTokenValue();
    }

    public Jwt createJwt(Users user) {
        return createJwt(String.valueOf(user.getId()));
    }

    public Jwt createJwt(String subject) {
        Oauth2Key oauth2Key = oauth2KeyProperties.jwk()
                .get(0);

        JwsHeader jwsHeader = JwsHeader.with(from(oauth2Key.type()))
                .keyId(oauth2Key.id())
                .build();

        Date now = new Date();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuer("http://localhost:8080")
                .audience(List.of("http://localhost:8080"))
                .issuedAt(now.toInstant())
                .notBefore(now.toInstant())
                .expiresAt(now.toInstant().plus(1, DAYS))
                .id(randomUUID().toString())
                .claim("scope", ALLOWED_SCOPES)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                jwsHeader,
                jwtClaimsSet
        );

        return jwtEncoder.encode(jwtEncoderParameters);
    }

}
