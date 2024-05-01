package com.pp.api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreatePostRequest(
        @NotBlank(message = "제목이 없습니다.")
        String title,
        @NotBlank(message = "내용이 없습니다.")
        String content,
        @Size(min = 1, max = 10, message = "이미지는 허용 개숫는 최소 {min}개 최대 {max}개 입니다.")
        List<Long> postImageFileUploadIds
) {
}
