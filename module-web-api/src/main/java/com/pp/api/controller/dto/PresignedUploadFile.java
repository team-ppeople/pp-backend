package com.pp.api.controller.dto;

public record PresignedUploadFile(
        Long fileUploadId,
        String fileName,
        String presignedUploadUrl,
        String fileUrl
) {

}
