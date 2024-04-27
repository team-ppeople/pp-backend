package com.pp.api.facade;

import com.pp.api.client.s3.S3PresignedClient;
import com.pp.api.controller.dto.PresignedUploadFile;
import com.pp.api.controller.dto.PresignedUploadUrlRequest;
import com.pp.api.controller.dto.PresignedUploadUrlResponse;
import com.pp.api.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PresignedUrlFacade {

    private final S3PresignedClient s3PresignedClient;

    private final UploadFileService uploadFileService;

    public PresignedUploadUrlResponse createPresignedUrl(
            List<PresignedUploadUrlRequest> requests
    ) {
        List<PresignedUploadFile> presignedUploadFiles = new ArrayList<>();
        for (PresignedUploadUrlRequest request : requests) {
            PresignedUploadFile presignedUploadFile = uploadFileService.savePresignedUploadFile(request);
            presignedUploadFiles.add(presignedUploadFile);
        }

        return new PresignedUploadUrlResponse(presignedUploadFiles);
    }

}
