package com.pp.api.service.domain;

import java.time.LocalDateTime;

public record CommentOfList(
        Long id,
        String content,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        Long creatorId
) {
}
