package com.pp.api.controller.dto;

import java.util.List;

public record FindUserProfileResponse(
        Long id,
        String nickname,
        String profileImageUrl,
        long postCount,
        long thumbsUpCount,
        List<UserCreatedPostResponse> posts
) {
}
