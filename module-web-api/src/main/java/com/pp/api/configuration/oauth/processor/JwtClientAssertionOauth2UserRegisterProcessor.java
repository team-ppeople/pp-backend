package com.pp.api.configuration.oauth.processor;

import com.pp.api.configuration.oauth.converter.JwtClientAssertionOauth2ClientCredentialsAuthenticationToken;
import com.pp.api.entity.enums.OauthUserClient;

public interface JwtClientAssertionOauth2UserRegisterProcessor {

    void process(JwtClientAssertionOauth2ClientCredentialsAuthenticationToken authentication);

    boolean support(OauthUserClient client);

}
