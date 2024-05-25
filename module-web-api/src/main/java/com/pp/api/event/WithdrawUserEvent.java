package com.pp.api.event;

import com.pp.api.entity.enums.OauthUserClient;

import static com.pp.api.entity.enums.OauthUserClient.APPLE;

public record WithdrawUserEvent(
        Long userId,
        String accessToken,
        String refreshToken,
        OauthUserClient client
) {

    public boolean isAppleOauthUser() {
        return this.client == APPLE;
    }

}
