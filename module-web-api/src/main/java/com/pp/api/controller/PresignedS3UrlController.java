package com.pp.api.controller;

import com.pp.api.controller.dto.PresignedUploadUrlResponse;
import com.pp.api.controller.dto.RestResponseWrapper;
import com.pp.api.controller.dto.PresignedUploadUrlRequests;
import com.pp.api.facade.PresignedUrlFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PresignedS3UrlController {

    private final PresignedUrlFacade presignedUrlFacade;

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_user.write')")
    @PostMapping(path = "/api/v1/presigned-urls/upload")
    public ResponseEntity<?> presignedUploadUrls(@RequestBody @Valid PresignedUploadUrlRequests requests) {
        PresignedUploadUrlResponse response = presignedUrlFacade.createPresignedUrl(requests.presignedUploadUrlRequests());
        return ResponseEntity.ok(RestResponseWrapper.from(response));
    }
}
