package com.pp.api.repository;

import com.pp.api.entity.Notices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticesRepository extends JpaRepository<Notices, Long> {
}
