package com.pp.api.repository.custom;

public interface CustomReportedCommentRepository {

    boolean existsByCommentIdAndReporterId(
            Long commentId,
            Long reporterId
    );

}
