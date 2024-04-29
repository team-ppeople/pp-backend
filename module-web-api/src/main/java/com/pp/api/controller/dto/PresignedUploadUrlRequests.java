package com.pp.api.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PresignedUploadUrlRequests(
        @Valid
        @NotEmpty(message = "요청 리스트가 비었습니다.")
        List<PresignedUploadUrlRequest> presignedUploadUrlRequests
) {
}
