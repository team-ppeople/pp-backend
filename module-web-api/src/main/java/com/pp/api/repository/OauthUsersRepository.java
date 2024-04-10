package com.pp.api.repository;

import com.pp.api.entity.OauthUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthUsersRepository extends JpaRepository<OauthUsers, Long> {
}
