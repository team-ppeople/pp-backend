package com.pp.api.service.domain;

import com.pp.api.entity.enums.OauthUserClient;

public record OauthUserAuthenticatedToken(
        Long id,
        OauthUserClient client,
        String accessToken,
        String refreshToken,
        Integer expiresIn,
        Long oauthUserId,
        Long userId
) {
}
