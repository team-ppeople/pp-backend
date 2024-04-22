package com.pp.api.fixture;

import com.pp.api.entity.OauthUsers;
import com.pp.api.entity.Users;
import com.pp.api.entity.enums.OauthUserClient;

import static com.pp.api.entity.enums.OauthUserClient.KAKAO;
import static java.util.UUID.randomUUID;

public class OauthUserFixture {

    private static final OauthUserClient DEFAULT_CLIENT = KAKAO;

    private static final String DEFAULT_SUBJECT = randomUUID().toString();

    public static OauthUsers from(
            OauthUserClient client,
            String subject,
            Users user
    ) {
        return OauthUsers.builder()
                .client(client)
                .subject(subject)
                .user(user)
                .build();
    }

    public static OauthUsers ofClient(
            OauthUserClient client,
            Users user
    ) {
        return OauthUsers.builder()
                .client(client)
                .subject(DEFAULT_SUBJECT)
                .user(user)
                .build();
    }

    public static OauthUsers ofClient(
            String subject,
            Users user
    ) {
        return OauthUsers.builder()
                .client(DEFAULT_CLIENT)
                .subject(subject)
                .user(user)
                .build();
    }

    public static OauthUsers ofUser(Users user) {
        return OauthUsers.builder()
                .client(DEFAULT_CLIENT)
                .subject(DEFAULT_SUBJECT)
                .user(user)
                .build();
    }

}
