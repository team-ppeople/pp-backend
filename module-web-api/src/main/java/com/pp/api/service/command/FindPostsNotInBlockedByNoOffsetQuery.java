package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

public record FindPostsNotInBlockedByNoOffsetQuery(
        Long lastId,
        @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개 입니다.")
        @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개 입니다.")
        int limit,
        List<Long> blockedIds
) {

    public static FindPostsNotInBlockedByNoOffsetQuery of(
            Long lastId,
            int limit,
            List<Long> blockedIds
    ) {
        return new FindPostsNotInBlockedByNoOffsetQuery(
                lastId,
                limit,
                blockedIds
        );
    }

    public static FindPostsNotInBlockedByNoOffsetQuery firstPage(
            int limit,
            List<Long> blockedIds
    ) {
        return new FindPostsNotInBlockedByNoOffsetQuery(
                null,
                limit,
                blockedIds

        );
    }

}
