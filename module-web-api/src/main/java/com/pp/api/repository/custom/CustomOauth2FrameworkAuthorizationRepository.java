package com.pp.api.repository.custom;

import com.pp.api.entity.OauthFrameworkAuthorization;

import java.util.Optional;

public interface CustomOauth2FrameworkAuthorizationRepository {

    Optional<OauthFrameworkAuthorization> findByAnyToken(String token);

}
