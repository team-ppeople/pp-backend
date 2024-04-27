package com.pp.api.unit.service;

import com.pp.api.client.s3.PresignedURL;
import com.pp.api.client.s3.S3PresignedClient;
import com.pp.api.controller.dto.PresignedUploadUrlRequest;
import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;
import com.pp.api.integration.AbstractIntegrationTestContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UploadFileServiceTest extends AbstractIntegrationTestContext {

    @Autowired
    private S3PresignedClient s3PresignedClient;

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

}
