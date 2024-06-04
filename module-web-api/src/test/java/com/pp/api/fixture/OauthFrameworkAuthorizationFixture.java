package com.pp.api.fixture;

import com.pp.api.entity.OauthFrameworkAuthorization;
import com.pp.api.entity.User;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

public class OauthFrameworkAuthorizationFixture {

    public static OauthFrameworkAuthorization ofUser(User user) {
        OauthFrameworkAuthorization oauthFrameworkAuthorization = new OauthFrameworkAuthorization();

        oauthFrameworkAuthorization.setId(randomUUID().toString());
        oauthFrameworkAuthorization.setRegisteredClientId(randomUUID().toString());
        oauthFrameworkAuthorization.setPrincipalName(randomUUID().toString());
        oauthFrameworkAuthorization.setAuthorizationGrantType(randomUUID().toString());
        oauthFrameworkAuthorization.setAuthorizedScopes(randomUUID().toString());
        oauthFrameworkAuthorization.setAttributes(randomUUID().toString());
        oauthFrameworkAuthorization.setState(randomUUID().toString());
        oauthFrameworkAuthorization.setAuthorizationCodeValue(randomUUID().toString());
        oauthFrameworkAuthorization.setAuthorizationCodeIssuedAt(now());
        oauthFrameworkAuthorization.setAuthorizationCodeExpiresAt(now());
        oauthFrameworkAuthorization.setAuthorizationCodeMetadata(randomUUID().toString());
        oauthFrameworkAuthorization.setAccessTokenValue(randomUUID().toString());
        oauthFrameworkAuthorization.setAccessTokenIssuedAt(now());
        oauthFrameworkAuthorization.setAccessTokenExpiresAt(now());
        oauthFrameworkAuthorization.setAccessTokenMetadata(randomUUID().toString());
        oauthFrameworkAuthorization.setAccessTokenType(randomUUID().toString());
        oauthFrameworkAuthorization.setAccessTokenScopes(randomUUID().toString());
        oauthFrameworkAuthorization.setRefreshTokenValue(randomUUID().toString());
        oauthFrameworkAuthorization.setRefreshTokenIssuedAt(now());
        oauthFrameworkAuthorization.setRefreshTokenExpiresAt(now());
        oauthFrameworkAuthorization.setRefreshTokenMetadata(randomUUID().toString());
        oauthFrameworkAuthorization.setOidcIdTokenValue(randomUUID().toString());
        oauthFrameworkAuthorization.setOidcIdTokenIssuedAt(now());
        oauthFrameworkAuthorization.setOidcIdTokenExpiresAt(now());
        oauthFrameworkAuthorization.setOidcIdTokenMetadata(randomUUID().toString());
        oauthFrameworkAuthorization.setOidcIdTokenClaims(randomUUID().toString());
        oauthFrameworkAuthorization.setUserCodeValue(randomUUID().toString());
        oauthFrameworkAuthorization.setUserCodeIssuedAt(now());
        oauthFrameworkAuthorization.setUserCodeExpiresAt(now());
        oauthFrameworkAuthorization.setUserCodeMetadata(randomUUID().toString());
        oauthFrameworkAuthorization.setDeviceCodeValue(randomUUID().toString());
        oauthFrameworkAuthorization.setUserCodeIssuedAt(now());
        oauthFrameworkAuthorization.setUserCodeExpiresAt(now());
        oauthFrameworkAuthorization.setDeviceCodeMetadata(randomUUID().toString());
        oauthFrameworkAuthorization.setUser(user);

        return oauthFrameworkAuthorization;
    }

}
