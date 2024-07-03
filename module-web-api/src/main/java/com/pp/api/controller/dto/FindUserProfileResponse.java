package com.pp.api.controller.dto;

import java.util.List;

public record FindUserProfileResponse(
        Long id,
        String nickname,
        String profileImageUrl,
        long postCount,
        long thumbsUpCount,
        boolean isBlocked,
        List<UserCreatedPostResponse> posts
) {

    public static FindUserProfileResponse of(
            Long id,
            String nickname,
            String profileImageUrl,
            long postCount,
            long thumbsUpCount,
            List<UserCreatedPostResponse> posts
    ) {
        return new FindUserProfileResponse(
                id,
                nickname,
                profileImageUrl,
                postCount,
                thumbsUpCount,
                false,
                posts
        );
    }

    public static FindUserProfileResponse blockedUser(
            Long id,
            String nickname,
            String profileImageUrl,
            long postCount,
            long thumbsUpCount
    ) {
        return new FindUserProfileResponse(
                id,
                nickname,
                profileImageUrl,
                postCount,
                thumbsUpCount,
                true,
                List.of()
        );
    }

}
