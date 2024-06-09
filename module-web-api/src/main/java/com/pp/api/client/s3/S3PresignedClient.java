package com.pp.api.client.s3;

import com.pp.api.configuration.aws.property.AwsS3Property;
import com.pp.api.controller.dto.PresignedUploadUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3PresignedClient {

    private final S3Presigner s3Presigner;

    private final AwsS3Property awsS3Property;

    public PresignedURL createPutPresignedUrl(PresignedUploadUrlRequest request) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(awsS3Property.bucket())
                .key(request.appendObjectKey(String.valueOf(UUID.randomUUID())))
                .contentLength(request.fileContentLength())
                .contentType(request.fileContentType().getType())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        return new PresignedURL(presignedRequest.url());
    }


}
