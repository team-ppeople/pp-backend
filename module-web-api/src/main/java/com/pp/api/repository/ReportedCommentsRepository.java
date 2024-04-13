package com.pp.api.repository;

import com.pp.api.entity.ReportedComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedCommentsRepository extends JpaRepository<ReportedComments, Long> {
}
