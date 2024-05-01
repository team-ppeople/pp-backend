package com.pp.api.repository.custom;

import com.pp.api.entity.Notice;

import java.util.List;

public interface CustomNoticeRepository {

    List<Notice> find(
            Long lastId,
            int limit
    );

}
