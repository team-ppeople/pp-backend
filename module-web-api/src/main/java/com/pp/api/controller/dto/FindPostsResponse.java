package com.pp.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record FindPostsResponse(
        List<PostResponse> posts
) {

    public record PostResponse(
            Long id,
            String thumbnailUrl,
            String title,
            @JsonFormat(pattern = "yyyy.MM.dd")
            LocalDateTime createDate
    ) {
    }

}
