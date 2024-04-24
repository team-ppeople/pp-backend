package com.pp.api.repository.custom;

import org.springframework.transaction.annotation.Transactional;

public interface CustomProfileImageRepository {

    @Transactional
    void deleteByUserId(Long userId);

}
