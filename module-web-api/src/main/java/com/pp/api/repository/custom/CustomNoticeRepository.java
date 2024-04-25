package com.pp.api.repository.custom;

import com.pp.api.entity.Notice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomNoticeRepository {

    @Transactional(readOnly = true)
    List<Notice> find(
            Long lastId,
            int limit
    );

}
