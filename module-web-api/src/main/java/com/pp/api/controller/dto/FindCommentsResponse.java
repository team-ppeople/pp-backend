package com.pp.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record FindCommentsResponse(
        List<CommentResponse> comments
) {

    public record CommentResponse(
            Long id,
            String content,
            @JsonFormat(pattern = "yyyy.MM.dd")
            LocalDateTime createDate
    ) {
    }

}
