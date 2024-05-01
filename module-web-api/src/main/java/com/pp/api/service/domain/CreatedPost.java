package com.pp.api.service.domain;

import java.time.LocalDateTime;

public record CreatedPost(
        Long id,
        String thumbnailUrl,
        String title,
        String content,
        LocalDateTime createdDate
) {
}
