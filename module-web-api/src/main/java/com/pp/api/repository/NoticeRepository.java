package com.pp.api.repository;

import com.pp.api.entity.Notice;
import com.pp.api.repository.custom.CustomNoticeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, CustomNoticeRepository {
}
