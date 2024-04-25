package com.pp.api.controller.dto;

public record UpdateUserRequest(
        String nickname,
        Long profileImageFileUploadId
) {
}
