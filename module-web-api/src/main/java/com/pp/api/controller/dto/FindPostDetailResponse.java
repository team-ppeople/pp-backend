package com.pp.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record FindPostDetailResponse(
        Long id,
        List<String> postImageUrls,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime createdDate,
        long thumbsUpCount,
        long commentCount,
        CreatorResponse createdUser,
        UserActionHistoryResponse userActionHistory
) {

    public record CreatorResponse(
            Long id,
            String nickname,
            String profileImageUrl
    ) {
    }

    public record UserActionHistoryResponse(
            boolean thumbsUpped,
            boolean reported
    ) {

    }

}
