package com.pp.api.fixture;

import com.pp.api.entity.OauthUserToken;
import com.pp.api.entity.OauthUser;
import com.pp.api.entity.enums.OauthUserClient;

import static com.pp.api.entity.enums.OauthUserClient.KAKAO;
import static java.lang.Integer.MAX_VALUE;
import static java.util.UUID.randomUUID;

public class OauthUserTokenFixture {

    private static final OauthUserClient DEFAULT_CLIENT = KAKAO;

    private static final String DEFAULT_ACCESS_TOKEN = randomUUID().toString();

    private static final String DEFAULT_REFRESH_TOKEN = randomUUID().toString();

    private static final int DEFAULT_EXPIRES_IN = MAX_VALUE;

    public static OauthUserToken from(
            OauthUserClient client,
            String accessToken,
            String refreshToken,
            int expiresIn,
            OauthUser oauthUser
    ) {
        return OauthUserToken.builder()
                .client(client)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .oauthUser(oauthUser)
                .build();
    }

    public static OauthUserToken ofClient(
            OauthUserClient client,
            OauthUser oauthUser
    ) {
        return OauthUserToken.builder()
                .client(client)
                .accessToken(DEFAULT_ACCESS_TOKEN)
                .refreshToken(DEFAULT_REFRESH_TOKEN)
                .expiresIn(DEFAULT_EXPIRES_IN)
                .oauthUser(oauthUser)
                .build();
    }

    public static OauthUserToken ofOauthUser(OauthUser oauthUser) {
        return OauthUserToken.builder()
                .client(DEFAULT_CLIENT)
                .accessToken(DEFAULT_ACCESS_TOKEN)
                .refreshToken(DEFAULT_REFRESH_TOKEN)
                .expiresIn(DEFAULT_EXPIRES_IN)
                .oauthUser(oauthUser)
                .build();
    }

}
