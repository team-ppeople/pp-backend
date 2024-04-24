package com.pp.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record FindUserProfileResponse(
        Long id,
        String nickname,
        String profileImageUrl,
        long postCount,
        long thumbsUpCount,
        List<UserCreatedPostResponse> post
) {

    public record UserCreatedPostResponse(
            Long id,
            String thumbnailUrl,
            String title,
            @JsonFormat(pattern = "yyyy.MM.dd") LocalDateTime createDate
    ) {
    }

}
