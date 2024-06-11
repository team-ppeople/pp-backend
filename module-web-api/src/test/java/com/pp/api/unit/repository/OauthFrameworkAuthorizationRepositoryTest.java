package com.pp.api.unit.repository;

import com.pp.api.entity.OauthFrameworkAuthorization;
import com.pp.api.entity.User;
import com.pp.api.fixture.OauthFrameworkAuthorizationFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.OauthFrameworkAuthorizationRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class OauthFrameworkAuthorizationRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private OauthFrameworkAuthorizationRepository oauthFrameworkAuthorizationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void Oauth_framework_authorization_엔티티를_영속화한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        assertThat(savedOauthFrameworkAuthorization.getId()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(oauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(savedOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(oauthFrameworkAuthorization.getPrincipalName());
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(oauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(savedOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(oauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(savedOauthFrameworkAuthorization.getAttributes()).isEqualTo(oauthFrameworkAuthorization.getAttributes());
        assertThat(savedOauthFrameworkAuthorization.getState()).isEqualTo(oauthFrameworkAuthorization.getState());
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(oauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(oauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenType());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(oauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(oauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(oauthFrameworkAuthorization.getUserCodeValue());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(oauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(savedOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(oauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(savedOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(oauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_id으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findById(oauthFrameworkAuthorization.getId())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_여러조건으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByAnyToken(oauthFrameworkAuthorization.getAccessTokenValue())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_state으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByState(oauthFrameworkAuthorization.getState())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_authorization_code_value으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByAuthorizationCodeValue(oauthFrameworkAuthorization.getAuthorizationCodeValue())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_access_token_value으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByAccessTokenValue(oauthFrameworkAuthorization.getAccessTokenValue())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_refresh_token_value으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByRefreshTokenValue(oauthFrameworkAuthorization.getRefreshTokenValue())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_oidc_token_value으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByOidcIdTokenValue(oauthFrameworkAuthorization.getOidcIdTokenValue())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_user_code_value으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByUserCodeValue(oauthFrameworkAuthorization.getUserCodeValue())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

    @Test
    void Oauth_framework_authorization_엔티티를_device_code_value으로_조회한다() {
        User user = userRepository.save(UserFixture.of());

        OauthFrameworkAuthorization oauthFrameworkAuthorization = OauthFrameworkAuthorizationFixture.ofUser(user);

        OauthFrameworkAuthorization savedOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.saveAndFlush(oauthFrameworkAuthorization);

        entityManager.clear();

        OauthFrameworkAuthorization foundOauthFrameworkAuthorization = oauthFrameworkAuthorizationRepository.findByDeviceCodeValue(oauthFrameworkAuthorization.getDeviceCodeValue())
                .orElseThrow();

        assertThat(foundOauthFrameworkAuthorization.getId()).isEqualTo(savedOauthFrameworkAuthorization.getId());
        assertThat(foundOauthFrameworkAuthorization.getRegisteredClientId()).isEqualTo(savedOauthFrameworkAuthorization.getRegisteredClientId());
        assertThat(foundOauthFrameworkAuthorization.getPrincipalName()).isEqualTo(savedOauthFrameworkAuthorization.getPrincipalName());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationGrantType()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationGrantType());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizedScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizedScopes());
        assertThat(foundOauthFrameworkAuthorization.getAttributes()).isEqualTo(savedOauthFrameworkAuthorization.getAttributes());
        assertThat(foundOauthFrameworkAuthorization.getState()).isEqualTo(savedOauthFrameworkAuthorization.getState());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isNotNull();
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

}
