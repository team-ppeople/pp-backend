package com.pp.api.util;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.pp.api.configuration.oauth.property.Oauth2KeyProperty;
import com.pp.api.configuration.oauth.property.Oauth2KeyProperty.Oauth2Key;
import com.pp.api.entity.User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.UUID.randomUUID;
import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.from;

@Component
public class JwtTestUtil {

    private static final Set<String> ALLOWED_SCOPES = Set.of(
            "user.read",
            "user.write",
            "post.read",
            "post.write",
            "file.write"
    );

    private final JwtEncoder jwtEncoder;

    private final Oauth2KeyProperty oauth2KeyProperty;

    public JwtTestUtil(
            JWKSource<SecurityContext> jwkSource,
            Oauth2KeyProperty oauth2KeyProperty
    ) {
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);
        this.oauth2KeyProperty = oauth2KeyProperty;
    }

    public String createBearerToken(User user) {
        return createBearerToken(String.valueOf(user.getId()));
    }

    public String createBearerToken(String subject) {
        return "Bearer " + createJwt(subject).getTokenValue();
    }

    public Jwt createJwt(User user) {
        return createJwt(String.valueOf(user.getId()));
    }

    public Jwt createJwt(String subject) {
        Oauth2Key oauth2Key = oauth2KeyProperty.jwk()
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
