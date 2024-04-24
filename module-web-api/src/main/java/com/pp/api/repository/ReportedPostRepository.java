package com.pp.api.repository;

import com.pp.api.entity.ReportedPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedPostRepository extends JpaRepository<ReportedPost, Long> {
}
