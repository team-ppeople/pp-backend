package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FindCommentsByNoOffsetQuery(
        @NotNull(message = "게시글 id가 없어요")
        Long postId,
        Long lastId,
        @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개에요")
        @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개에요")
        int limit
) {

    public static FindCommentsByNoOffsetQuery of(
            Long postId,
            Long lastId,
            int limit
    ) {
        return new FindCommentsByNoOffsetQuery(
                postId,
                lastId,
                limit
        );
    }

    public static FindCommentsByNoOffsetQuery firstPage(
            Long postId,
            int limit
    ) {
        return new FindCommentsByNoOffsetQuery(
                postId,
                null,
                limit
        );
    }

}
