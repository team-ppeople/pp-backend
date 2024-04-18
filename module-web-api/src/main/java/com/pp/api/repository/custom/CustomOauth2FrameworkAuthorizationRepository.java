package com.pp.api.repository.custom;

import com.pp.api.entity.OauthFrameworkAuthorization;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomOauth2FrameworkAuthorizationRepository {

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByAnyToken(String token);

}
