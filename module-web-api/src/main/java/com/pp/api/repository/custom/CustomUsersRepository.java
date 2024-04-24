package com.pp.api.repository.custom;

import com.pp.api.entity.Users;

import java.util.Optional;

public interface CustomUsersRepository {

    Optional<Users> findWithProfileImagesById(Long userId);

}
