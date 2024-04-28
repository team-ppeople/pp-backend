package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class FindNoticesByNoOffsetQuery extends CommandSelfValidator<FindNoticesByNoOffsetQuery> {

    private final Long lastId;

    @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개 입니다.")
    @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개 입니다.")
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
