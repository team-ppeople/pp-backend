package com.pp.api.repository.custom;

import com.pp.api.entity.Comment;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> findByPostId(
            Long postId,
            Long lastId,
            int limit
    );

}
