package com.pp.api.controller.dto;

public record PresignedUploadFile(
        Long fileUploadId,
        String presignedUploadUrl,
        String fileUrl
) {

}
