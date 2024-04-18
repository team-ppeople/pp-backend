package com.pp.api.repository;

import com.pp.api.entity.OauthFrameworkAuthorization;
import com.pp.api.repository.custom.CustomOauth2FrameworkAuthorizationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface Oauth2FrameworkAuthorizationRepository extends JpaRepository<OauthFrameworkAuthorization, String>,
        CustomOauth2FrameworkAuthorizationRepository {

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByState(String state);

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByAuthorizationCodeValue(String authorizationCode);

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByAccessTokenValue(String accessToken);

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByRefreshTokenValue(String refreshToken);

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByOidcIdTokenValue(String idToken);

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByUserCodeValue(String userCode);

    @Transactional(readOnly = true)
    Optional<OauthFrameworkAuthorization> findByDeviceCodeValue(String deviceCode);

}
