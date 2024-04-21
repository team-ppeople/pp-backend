package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FindUserCreatedPostsByNoOffsetQuery extends CommandSelfValidator<FindUserCreatedPostsByNoOffsetQuery> {

    @NotNull(message = "작성자 id가 없습니다.")
    private final Long creatorId;

    private final Long lastId;

    @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개 입니다.")
    @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개 입니다.")
    private final int limit;

    private FindUserCreatedPostsByNoOffsetQuery(
            Long creatorId,
            Long lastId,
            int limit
    ) {
        this.creatorId = creatorId;
        this.lastId = lastId;
        this.limit = limit;
        this.validate();
    }

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