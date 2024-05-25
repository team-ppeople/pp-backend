package com.pp.api.repository;

import com.pp.api.entity.OauthUserToken;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.repository.custom.CustomOauthUserTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthUserTokenRepository extends JpaRepository<OauthUserToken, Long>, CustomOauthUserTokenRepository {

    Optional<OauthUserToken> findByOauthUserIdAndClient(
            Long oauthUserId,
            OauthUserClient client
    );

}
