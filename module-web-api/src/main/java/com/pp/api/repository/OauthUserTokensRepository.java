package com.pp.api.repository;

import com.pp.api.entity.OauthUserTokens;
import com.pp.api.entity.enums.OauthUserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OauthUserTokensRepository extends JpaRepository<OauthUserTokens, Long> {

    @Transactional(readOnly = true)
    Optional<OauthUserTokens> findByOauthUserIdAndClient(
            Long oauthUserId,
            OauthUserClient client
    );

}
