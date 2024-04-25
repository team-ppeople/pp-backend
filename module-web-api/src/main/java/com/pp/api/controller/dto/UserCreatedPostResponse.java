package com.pp.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserCreatedPostResponse(
        Long id,
        String thumbnailUrl,
        String title,
        @JsonFormat(pattern = "yyyy.MM.dd")
        LocalDateTime createDate
) {
}
