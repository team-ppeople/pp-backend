package com.pp.api.service.domain;

import java.time.LocalDateTime;

public record UserWithProfile(
        Long id,
        String nickname,
        String email,
        String profileImageUrl,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
