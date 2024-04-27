package com.pp.api.service;

import com.pp.api.client.S3PresignedClient;
import com.pp.api.controller.dto.PresignedUploadFile;
import com.pp.api.controller.dto.PresignedUploadUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PresignedS3UrlService {

    private final S3PresignedClient s3PresignedClient;

    public List<PresignedUploadFile> createPresignedUrl(List<PresignedUploadUrlRequest> requests) {
        List<PresignedUploadFile> presignedUploadFiles = new ArrayList<>();
        for (PresignedUploadUrlRequest request : requests) {
            String url = s3PresignedClient.createPutPresignedUrl(request.fileName());
            System.out.println(url);
        }
        return presignedUploadFiles;
    }

}
