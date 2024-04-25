package com.pp.api.service.domain;

import java.time.LocalDateTime;

public record NoticeOfList(
        Long id,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
