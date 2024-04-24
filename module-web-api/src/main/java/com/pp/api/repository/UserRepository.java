package com.pp.api.repository;

import com.pp.api.entity.User;
import com.pp.api.repository.custom.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
}
