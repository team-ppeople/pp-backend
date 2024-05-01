package com.pp.api.repository;

import com.pp.api.entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {

    boolean existsByClientSubject(String clientSubject);

    Optional<OauthUser> findByClientSubject(String clientSubject);

}
