package com.pp.api.repository;

import com.pp.api.entity.OauthUsers;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OauthUsersRepository extends JpaRepository<OauthUsers, Long> {

    @Transactional(readOnly = true)
    boolean existsByClientSubject(String clientSubject);

    @Transactional(readOnly = true)
    Optional<OauthUsers> findByClientSubject(String clientSubject);

}
