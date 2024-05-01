package com.pp.api.repository;

import com.pp.api.entity.OauthFrameworkAuthorization;
import com.pp.api.repository.custom.CustomOauth2FrameworkAuthorizationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthFrameworkAuthorizationRepository extends JpaRepository<OauthFrameworkAuthorization, String>,
        CustomOauth2FrameworkAuthorizationRepository {

    Optional<OauthFrameworkAuthorization> findByState(String state);

    Optional<OauthFrameworkAuthorization> findByAuthorizationCodeValue(String authorizationCode);

    Optional<OauthFrameworkAuthorization> findByAccessTokenValue(String accessToken);

    Optional<OauthFrameworkAuthorization> findByRefreshTokenValue(String refreshToken);

    Optional<OauthFrameworkAuthorization> findByOidcIdTokenValue(String idToken);

    Optional<OauthFrameworkAuthorization> findByUserCodeValue(String userCode);

    Optional<OauthFrameworkAuthorization> findByDeviceCodeValue(String deviceCode);

}
