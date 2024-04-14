package com.pp.api.configuration;

import com.pp.api.entity.enums.OauthUserClient;

public interface JwtClientAssertionOauth2UserRegisterProcessor {

    void process(JwtClientAssertionOauth2ClientCredentialsAuthenticationToken authentication);

    boolean support(OauthUserClient client);

}
