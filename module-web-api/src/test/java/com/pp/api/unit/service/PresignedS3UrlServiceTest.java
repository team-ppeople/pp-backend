package com.pp.api.unit.service;

import com.pp.api.client.S3PresignedClient;
import com.pp.api.integration.AbstractIntegrationTestContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PresignedS3UrlServiceTest extends AbstractIntegrationTestContext {

    @Autowired
    private S3PresignedClient s3PresignedClient;

    @Test
    public void Presigned_URL을_발급받는다() {
        // given
        String fileName = "pp_upload_test_file.jpg";

        // when
        String url = s3PresignedClient.createPutPresignedUrl(fileName);

        // then
        assertThat(url.contains(fileName)).isTrue();
    }

}
