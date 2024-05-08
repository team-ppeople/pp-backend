package com.pp.api.unit.service;

import com.pp.api.client.s3.PresignedURL;
import com.pp.api.client.s3.S3PresignedClient;
import com.pp.api.configuration.aws.property.AwsS3Property;
import com.pp.api.controller.dto.PresignedUploadUrlRequest;
import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;
import com.pp.api.integration.AbstractIntegrationTestContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class UploadFileServiceTest extends AbstractIntegrationTestContext {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private S3PresignedClient s3PresignedClient;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private AwsS3Property awsS3Property;

    @Test
    public void Presigned_URL을_발급받는다() {
        // given
        PresignedUploadUrlRequest request = new PresignedUploadUrlRequest(
                UploadFileContentType.IMAGE_JPEG,
                "testfile",
                1200L,
                UploadFileType.POST_IMAGE
        );

        // when
        PresignedURL url = s3PresignedClient.createPutPresignedUrl(request);
        String fileUrl = url.getFileUrl();
        String uploadUrl = url.getUploadUrl();

        // then
        assertThat(fileUrl.contains(request.toFileKeyObjectPath())).isTrue();
        assertThat(uploadUrl.contains(request.toFileKeyObjectPath())).isTrue();
    }

    @Test
    public void 발급받은_Presigned_URL으로_파일을_업로드_할수있다() throws Exception {
        // given
        String filename = "application-test.yml";

        // LocalStack S3에서는 content-type, content-length 검증은 하지 않는 것으로 보인다.
        PresignedUploadUrlRequest request = new PresignedUploadUrlRequest(
                UploadFileContentType.IMAGE_JPEG,
                filename,
                1200L,
                UploadFileType.POST_IMAGE
        );

        // when
        String preSignedUploadUrl = s3PresignedClient.createPutPresignedUrl(request)
                .getUploadUrl();

        uploadByPreSignedUrl(preSignedUploadUrl, filename);

        // then
        existsByKey(parseUploadedKeyFrom(preSignedUploadUrl));
    }

    private String parseUploadedKeyFrom(String preSignedUrl) throws Exception {
        return new URL(preSignedUrl).getPath()
                .replace("/" + awsS3Property.bucket() + "/", "");
    }

    private void uploadByPreSignedUrl(String preSignedUrl, String filename) throws Exception {
        try (InputStream inputStream = new ClassPathResource(filename).getInputStream()) {
            byte[] bytes = StreamUtils.copyToByteArray(inputStream);

            restTemplate.put(
                    new URI(preSignedUrl),
                    new HttpEntity<>(bytes)
            );
        }
    }

    private void existsByKey(String key) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(awsS3Property.bucket())
                .key(key)
                .build();

        s3Client.headObject(headObjectRequest);
    }

}
