package com.pp.api.service.command;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class FindNoticesByNoOffsetQuery extends CommandSelfValidator<FindNoticesByNoOffsetQuery> {

    private final Long lastId;

    @Range(min = 10, max = 100, message = "조회 허용 갯수는 최소 {min}개 최대 {max}개 입니다.")
    private final int limit;

    private FindNoticesByNoOffsetQuery(
            Long lastId,
            int limit
    ) {
        this.lastId = lastId;
        this.limit = limit;
        this.validate();
    }

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
