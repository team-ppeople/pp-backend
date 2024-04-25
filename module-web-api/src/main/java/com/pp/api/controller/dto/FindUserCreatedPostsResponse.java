package com.pp.api.controller.dto;

import java.util.List;

public record FindUserCreatedPostsResponse(
        List<UserCreatedPostResponse> posts
) {
}
