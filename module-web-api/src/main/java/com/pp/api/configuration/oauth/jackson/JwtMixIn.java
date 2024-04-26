package com.pp.api.configuration.oauth.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;

@JsonTypeInfo(use = CLASS)
@JsonAutoDetect(
        fieldVisibility = ANY,
        getterVisibility = NONE,
        isGetterVisibility = NONE
)
public abstract class JwtMixIn {

    @JsonCreator
    JwtMixIn(
            @JsonProperty(value = "tokenValue") String tokenValue,
            @JsonProperty(value = "issuedAt") Instant issuedAt,
            @JsonProperty(value = "expiresAt") Instant expiresAt,
            @JsonProperty(value = "headers") Map<String, Object> headers,
            @JsonProperty(value = "claims") Map<String, Object> claims
    ) {
    }

}
