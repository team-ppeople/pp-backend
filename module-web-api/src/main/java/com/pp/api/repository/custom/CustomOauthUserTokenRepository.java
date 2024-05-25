package com.pp.api.repository.custom;

import com.pp.api.entity.OauthUserToken;

import java.util.Optional;

public interface CustomOauthUserTokenRepository {

    Optional<OauthUserToken> findByUserId(Long userId);

}
