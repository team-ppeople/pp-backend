package com.pp.api.service.command;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class FindPostOfListByNoOffsetQuery extends CommandSelfValidator<FindPostOfListByNoOffsetQuery> {

    private final Long lastId;

    @Range(min = 10, max = 100, message = "조회 허용 갯수는 최소 {min}개 최대 {max}개 입니다.")
    private final int limit;

    private FindPostOfListByNoOffsetQuery(
            Long lastId,
            int limit
    ) {
        this.lastId = lastId;
        this.limit = limit;
        this.validate();
    }

    public static FindPostOfListByNoOffsetQuery of(
            Long lastId,
            int limit
    ) {
        return new FindPostOfListByNoOffsetQuery(
                lastId,
                limit
        );
    }

    public static FindPostOfListByNoOffsetQuery firstPage(
            int limit
    ) {
        return new FindPostOfListByNoOffsetQuery(
                null,
                limit
        );
    }

}
