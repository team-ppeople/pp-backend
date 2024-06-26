package com.pp.api.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PresignedUploadUrlRequests(
        @Valid
        @NotEmpty(message = "요청 내용이 없어요")
        List<PresignedUploadUrlRequest> presignedUploadUrlRequests
) {
}
