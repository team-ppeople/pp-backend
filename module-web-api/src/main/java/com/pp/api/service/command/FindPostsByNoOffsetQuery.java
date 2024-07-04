package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record FindPostsByNoOffsetQuery(
        Long lastId,
        @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개에요")
        @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개에요")
        int limit
) {

    public static FindPostsByNoOffsetQuery of(
            Long lastId,
            int limit
    ) {
        return new FindPostsByNoOffsetQuery(
                lastId,
                limit
        );
    }

    public static FindPostsByNoOffsetQuery firstPage(int limit) {
        return new FindPostsByNoOffsetQuery(
                null,
                limit
        );
    }

}
