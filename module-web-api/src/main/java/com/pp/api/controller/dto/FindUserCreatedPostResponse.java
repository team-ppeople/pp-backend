package com.pp.api.controller.dto;

import java.util.List;

public record FindUserCreatedPostResponse(
        List<UserCreatedPostResponse> posts
) {
}
