package com.pp.api.repository.custom;

public interface CustomReportedPostRepository {

    boolean existsByPostIdAndReporterId(
            Long postId,
            Long reporterId
    );

}
