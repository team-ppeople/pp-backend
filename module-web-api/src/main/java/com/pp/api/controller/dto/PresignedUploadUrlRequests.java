package com.pp.api.controller.dto;

import jakarta.validation.Valid;

import java.util.List;

public record PresignedUploadUrlRequests(
        @Valid
        List<PresignedUploadUrlRequest> presignedUploadUrlRequests
) {
}
