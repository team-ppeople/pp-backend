package com.pp.api.util;

import com.pp.api.configuration.aws.property.AwsS3Property;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Component
public class LocalStackSetUpUtil {

    private final AwsS3Property awsS3Property;

    private final S3Client s3Client;

    public LocalStackSetUpUtil(
            AwsS3Property awsS3Property,
            S3Client s3Client
    ) {
        this.awsS3Property = awsS3Property;
        this.s3Client = s3Client;
    }

    public void setUp() {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(awsS3Property.bucket())
                .build();

        s3Client.createBucket(createBucketRequest);
    }

}
