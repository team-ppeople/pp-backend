package com.pp.api.repository;

import com.pp.api.entity.Comment;
import com.pp.api.repository.custom.CustomCommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
}
