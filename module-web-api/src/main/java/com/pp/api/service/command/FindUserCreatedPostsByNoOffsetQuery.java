package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FindUserCreatedPostsByNoOffsetQuery(
        @NotNull(message = "작성자 id가 없어요")
        Long creatorId,
        Long lastId,
        @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개에요")
        @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개에요")
        int limit
) {

    public static FindUserCreatedPostsByNoOffsetQuery of(
            Long creatorId,
            Long lastId,
            int limit
    ) {
        return new FindUserCreatedPostsByNoOffsetQuery(
                creatorId,
                lastId,
                limit
        );
    }

    public static FindUserCreatedPostsByNoOffsetQuery firstPage(
            Long creatorId,
            int limit
    ) {
        return new FindUserCreatedPostsByNoOffsetQuery(
                creatorId,
                null,
                limit
        );
    }

}
