package com.pp.api.repository;

import com.pp.api.entity.OauthUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {

    @Transactional(readOnly = true)
    boolean existsByClientSubject(String clientSubject);

    @Transactional(readOnly = true)
    Optional<OauthUser> findByClientSubject(String clientSubject);

}
