package com.pp.api.repository.custom;

import com.pp.api.entity.User;

import java.util.Optional;

public interface CustomUserRepository {

    Optional<User> findWithProfileImagesById(Long userId);

    void deleteCascadeById(Long userId);

}
