package com.pp.api.fixture;

import com.pp.api.entity.OauthUser;
import com.pp.api.entity.User;
import com.pp.api.entity.enums.OauthUserClient;

import static com.pp.api.entity.enums.OauthUserClient.KAKAO;
import static java.util.UUID.randomUUID;

public class OauthUserFixture {

    private static final OauthUserClient DEFAULT_CLIENT = KAKAO;

    private static final String DEFAULT_SUBJECT = randomUUID().toString();

    public static OauthUser from(
            OauthUserClient client,
            String subject,
            User user
    ) {
        return OauthUser.builder()
                .client(client)
                .subject(subject)
                .user(user)
                .build();
    }

    public static OauthUser ofClient(
            OauthUserClient client,
            User user
    ) {
        return OauthUser.builder()
                .client(client)
                .subject(DEFAULT_SUBJECT)
                .user(user)
                .build();
    }

    public static OauthUser ofClient(
            String subject,
            User user
    ) {
        return OauthUser.builder()
                .client(DEFAULT_CLIENT)
                .subject(subject)
                .user(user)
                .build();
    }

    public static OauthUser ofUser(User user) {
        return OauthUser.builder()
                .client(DEFAULT_CLIENT)
                .subject(DEFAULT_SUBJECT)
                .user(user)
                .build();
    }

}
