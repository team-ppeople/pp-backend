package com.pp.api.repository.custom;

import com.pp.api.entity.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {

    Optional<User> findWithProfileImagesById(Long userId);

    List<User> findWithProfileImagesByIds(List<Long> userIds);

    void deleteCascadeById(Long userId);

}
