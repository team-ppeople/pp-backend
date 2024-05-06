package com.pp.api.repository.custom;

public interface CustomReportedPostRepository {

    boolean existsByPostId(Long postId);

    boolean existsByPostIdAndReporterId(
            Long postId,
            Long reporterId
    );

}
