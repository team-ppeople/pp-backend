package com.pp.api.repository.custom;

import com.pp.api.entity.OauthFrameworkAuthorization;

import java.util.Optional;

public interface CustomOauthFrameworkAuthorizationRepository {

    Optional<OauthFrameworkAuthorization> findByAnyToken(String token);

    void deleteByUserId(Long userId);

}
