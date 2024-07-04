package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record FindNoticesByNoOffsetQuery(
        Long lastId,
        @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개에요")
        @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개에요")
        int limit
) {


    public static FindNoticesByNoOffsetQuery of(
            Long lastId,
            int limit
    ) {
        return new FindNoticesByNoOffsetQuery(
                lastId,
                limit
        );
    }

    public static FindNoticesByNoOffsetQuery firstPage(
            int limit
    ) {
        return new FindNoticesByNoOffsetQuery(
                null,
                limit
        );
    }

}
