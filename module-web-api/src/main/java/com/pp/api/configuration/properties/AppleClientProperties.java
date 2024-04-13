package com.pp.api.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "client.apple")
public record AppleClientProperties(
        @NotBlank String baseUrl,
        @NotBlank String privateKey,
        @NotBlank String clientId,
        @NotBlank String clientSecretKid,
        @NotBlank String clientSecretIssuer,
        @NotNull Integer clientSecretExpirationDays,
        @NotBlank String tokenEndpoint,
        @NotBlank String revokeEndpoint,
        @NotNull Integer connectionTimeout,
        @NotNull Integer readTimeout,
        @NotNull Integer writeTimeout
) {
}