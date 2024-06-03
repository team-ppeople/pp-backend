package com.pp.api.event;

import com.pp.api.entity.enums.OauthUserClient;

import static com.pp.api.entity.enums.OauthUserClient.APPLE;

public record WithdrawOauthUserEvent(
        Long userId,
        String oauthUserAccessToken,
        String oauthUserRefreshToken,
        OauthUserClient client
) {

    public boolean isAppleOauthUser() {
        return this.client == APPLE;
    }

}
