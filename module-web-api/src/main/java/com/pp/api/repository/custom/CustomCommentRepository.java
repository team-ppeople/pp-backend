package com.pp.api.repository.custom;

import com.pp.api.entity.Comment;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> findByPostId(
            Long postId,
            Long lastId,
            int limit
    );

    List<Comment> findNotInBlockedUsersByPostId(
            Long postId,
            Long lastId,
            int limit,
            List<Long> blockedIds
    );

    long countByPostId(Long postId);

    long countNotInBlockedUserByPostId(
            Long postId,
            List<Long> blockedIds
    );

}
