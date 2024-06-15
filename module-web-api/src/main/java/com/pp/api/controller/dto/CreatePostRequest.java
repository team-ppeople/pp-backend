package com.pp.api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreatePostRequest(
        @NotBlank(message = "제목이 없습니다.")
        String title,
        @NotBlank(message = "내용이 없습니다.")
        String content,
        @Size(max = 10, message = "이미지 허용 갯수는 최대 {max}개 입니다.")
        List<Long> postImageFileUploadIds
) {
}
