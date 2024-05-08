package com.pp.api.configuration.aws;

import com.pp.api.configuration.aws.property.AwsS3Property;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

import static org.springframework.util.StringUtils.hasText;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = AwsS3Property.class)
public class AwsS3Configuration {

    private final AwsS3Property awsS3Property;

    @Bean
    public S3Client s3Client() {
        S3ClientBuilder s3ClientBuilder = S3Client.builder()
                .credentialsProvider(awsCredentialsProvider(awsS3Property))
                .region(Region.of(awsS3Property.region()));

        if (hasText(awsS3Property.endpointOverrideUri())) {
            s3ClientBuilder.endpointOverride(URI.create(awsS3Property.endpointOverrideUri()));
        }

        return s3ClientBuilder.build();
    }

    @Bean
    public S3Presigner S3Presigner() {
        S3Presigner.Builder s3PresignerBuilder = S3Presigner.builder()
                .credentialsProvider(awsCredentialsProvider(awsS3Property))
                .region(Region.of(awsS3Property.region()));

        if (hasText(awsS3Property.endpointOverrideUri())) {
            s3PresignerBuilder.endpointOverride(URI.create(awsS3Property.endpointOverrideUri()));
        }

        return s3PresignerBuilder.build();
    }

    private AwsCredentialsProvider awsCredentialsProvider(AwsS3Property awsS3Property) {
        if (hasText(awsS3Property.accessKey()) && hasText(awsS3Property.secretKey())) {
            AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(
                    awsS3Property.accessKey(),
                    awsS3Property.secretKey()
            );

            return StaticCredentialsProvider.create(awsBasicCredentials);
        }

        return DefaultCredentialsProvider.create();
    }

}
