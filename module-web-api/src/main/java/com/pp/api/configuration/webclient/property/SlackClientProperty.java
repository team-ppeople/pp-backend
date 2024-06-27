package com.pp.api.configuration.webclient.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "client.slack")
public record SlackClientProperty(
        @NotBlank String baseUrl,
        @NotBlank String token,
        @NotBlank String channelId,
        @NotNull Integer connectionTimeout,
        @NotNull Integer readTimeout,
        @NotNull Integer writeTimeout,
        @NotNull Boolean enabled
) {
}