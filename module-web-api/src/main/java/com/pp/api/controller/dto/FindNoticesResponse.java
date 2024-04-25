package com.pp.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record FindNoticesResponse(
        List<NoticeResponse> notices
) {

    public record NoticeResponse(
            Long id,
            String title,
            String content,
            @JsonFormat(pattern = "yyyy.MM.dd")
            LocalDateTime createDate
    ) {
    }

}
