package com.pp.api.service.domain;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetail(
        Long id,
        List<String> postImageUrls,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        Long creatorId
) {
}
