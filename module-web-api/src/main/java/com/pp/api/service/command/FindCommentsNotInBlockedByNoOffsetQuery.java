package com.pp.api.service.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class FindCommentsNotInBlockedByNoOffsetQuery extends CommandSelfValidator<FindCommentsNotInBlockedByNoOffsetQuery> {

    @NotNull(message = "게시글 id가 없어요")
    private final Long postId;

    private final Long lastId;

    @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개에요")
    @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개에요")
    private final int limit;

    private final List<Long> blockedIds;

    private FindCommentsNotInBlockedByNoOffsetQuery(
            Long postId,
            Long lastId,
            int limit,
            List<Long> blockedIds
    ) {
        this.postId = postId;
        this.lastId = lastId;
        this.limit = limit;
        this.blockedIds = blockedIds;
        this.validate();
    }

    public static FindCommentsNotInBlockedByNoOffsetQuery of(
            Long postId,
            Long lastId,
            int limit,
            List<Long> blockedIds
    ) {
        return new FindCommentsNotInBlockedByNoOffsetQuery(
                postId,
                lastId,
                limit,
                blockedIds
        );
    }

    public static FindCommentsNotInBlockedByNoOffsetQuery firstPage(
            Long postId,
            int limit,
            List<Long> blockedIds
    ) {
        return new FindCommentsNotInBlockedByNoOffsetQuery(
                postId,
                null,
                limit,
                blockedIds
        );
    }

}
