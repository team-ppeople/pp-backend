package com.pp.api.service.domain;

import java.time.LocalDateTime;

public record PostOfList(
        Long id,
        String thumbnailUrl,
        String title,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
