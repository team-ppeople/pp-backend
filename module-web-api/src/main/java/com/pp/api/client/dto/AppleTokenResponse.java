package com.pp.api.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class AppleTokenResponse {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "id_token")
    private String idToken;

}
