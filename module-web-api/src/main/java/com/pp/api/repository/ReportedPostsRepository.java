package com.pp.api.repository;

import com.pp.api.entity.ReportedPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedPostsRepository extends JpaRepository<ReportedPosts, Long> {
}
