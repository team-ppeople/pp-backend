package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.util.List;

@Getter
public class FindPostsNotInBlockedByNoOffsetQuery extends CommandSelfValidator<FindPostsNotInBlockedByNoOffsetQuery> {

    private final Long lastId;

    @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개 입니다.")
    @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개 입니다.")
    private final int limit;

    private final List<Long> blockedIds;

    private FindPostsNotInBlockedByNoOffsetQuery(
            Long lastId,
            int limit,
            List<Long> blockedIds
    ) {
        this.lastId = lastId;
        this.limit = limit;
        this.blockedIds = blockedIds;
        this.validate();
    }

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
