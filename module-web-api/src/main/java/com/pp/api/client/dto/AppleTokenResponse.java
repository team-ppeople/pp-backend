package com.pp.api.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppleTokenResponse(
        @JsonProperty(value = "access_token") String accessToken,
        @JsonProperty(value = "token_type") String tokenType,
        @JsonProperty(value = "expires_in") Integer expiresIn,
        @JsonProperty(value = "refresh_token") String refreshToken,
        @JsonProperty(value = "id_token") String idToken
) {
}
