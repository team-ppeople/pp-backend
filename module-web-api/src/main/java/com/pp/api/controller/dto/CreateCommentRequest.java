package com.pp.api.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank(message = "내용이 없어요")
        String content
) {
}
