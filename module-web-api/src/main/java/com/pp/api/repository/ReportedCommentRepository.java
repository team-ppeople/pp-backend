package com.pp.api.repository;

import com.pp.api.entity.ReportedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedCommentRepository extends JpaRepository<ReportedComment, Long> {
}
