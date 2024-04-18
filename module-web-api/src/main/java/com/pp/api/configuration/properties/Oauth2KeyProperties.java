package com.pp.api.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "oauth2")
public record Oauth2KeyProperties(
        @NotEmpty List<Oauth2Key> jwk
) {

    @Validated
    public record Oauth2Key(
            @NotBlank String id,
            @NotBlank String type,
            @NotBlank String publicKey,
            @NotBlank String privateKey
    ) {
    }

}