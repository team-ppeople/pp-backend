package com.pp.api.controller;

import com.pp.api.controller.dto.PresignedUploadFile;
import com.pp.api.controller.dto.PresignedUploadUrlRequest;
import com.pp.api.controller.dto.PresignedUploadUrlResponse;
import com.pp.api.service.PresignedS3UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PresignedS3UrlController {

    private final PresignedS3UrlService PresignedS3UrlService;

    @PostMapping(path = "/api/v1/presigned/upload-urls")
    public ResponseEntity<?> presignedUploadUrls(@RequestBody List<PresignedUploadUrlRequest> requests) {
        List<PresignedUploadFile> presignedUploadFiles = PresignedS3UrlService.createPresignedUrl(requests);
        return ResponseEntity.ok(new PresignedUploadUrlResponse(presignedUploadFiles));
    }
}
