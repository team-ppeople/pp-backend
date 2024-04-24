package com.pp.api.repository;

import com.pp.api.entity.OauthUserToken;
import com.pp.api.entity.enums.OauthUserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OauthUserTokenRepository extends JpaRepository<OauthUserToken, Long> {

    @Transactional(readOnly = true)
    Optional<OauthUserToken> findByOauthUserIdAndClient(
            Long oauthUserId,
            OauthUserClient client
    );

}
