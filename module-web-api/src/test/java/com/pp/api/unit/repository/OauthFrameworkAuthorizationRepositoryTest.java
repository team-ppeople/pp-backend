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
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(oauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(oauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(oauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenType());
        assertThat(savedOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(oauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(oauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(oauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(oauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(savedOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(oauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(savedOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(oauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(oauthFrameworkAuthorization.getUserCodeValue());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(oauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(oauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(oauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(savedOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(oauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(oauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(savedOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(oauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
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
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAuthorizationCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAuthorizationCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenType()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenType());
        assertThat(foundOauthFrameworkAuthorization.getAccessTokenScopes()).isEqualTo(savedOauthFrameworkAuthorization.getAccessTokenScopes());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getRefreshTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getRefreshTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenValue()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenValue());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenMetadata());
        assertThat(foundOauthFrameworkAuthorization.getOidcIdTokenClaims()).isEqualTo(savedOauthFrameworkAuthorization.getOidcIdTokenClaims());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeMetadata());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeValue()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeValue());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeIssuedAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeIssuedAt());
        assertThat(foundOauthFrameworkAuthorization.getUserCodeExpiresAt()).isEqualTo(savedOauthFrameworkAuthorization.getUserCodeExpiresAt());
        assertThat(foundOauthFrameworkAuthorization.getDeviceCodeMetadata()).isEqualTo(savedOauthFrameworkAuthorization.getDeviceCodeMetadata());
    }

}
