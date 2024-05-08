package com.pp.api.configuration.aws.property;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "aws.s3")
public record AwsS3Property(
        String accessKey,
        String secretKey,
        @NotBlank String bucket,
        @NotBlank String region,
        String endpointOverrideUri
) {
}