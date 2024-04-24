package com.pp.api.repository;

import com.pp.api.entity.Users;
import com.pp.api.repository.custom.CustomUsersRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long>, CustomUsersRepository {
}
