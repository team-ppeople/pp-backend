package com.pp.api.repository.custom;

import org.springframework.transaction.annotation.Transactional;

public interface CustomProfileImagesRepository {

    @Transactional
    void deleteByUserId(Long userId);

}
