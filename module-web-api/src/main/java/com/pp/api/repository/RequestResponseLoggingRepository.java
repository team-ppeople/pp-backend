package com.pp.api.repository;

import com.pp.api.entity.RequestResponseLogging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestResponseLoggingRepository extends JpaRepository<RequestResponseLogging, Long> {
}
