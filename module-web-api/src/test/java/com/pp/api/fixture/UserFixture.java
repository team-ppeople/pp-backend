package com.pp.api.fixture;

import com.pp.api.entity.Users;

public class UserFixture {

    private static final String DEFAULT_NICKNAME = "sinbom";

    private static final String DEFAULT_EMAIL = "dev.sinbom@gmail.com";

    public static Users from(
            String nickname,
            String email
    ) {
        return Users.builder()
                .nickname(nickname)
                .email(email)
                .build();
    }

    public static Users ofNickname(String nickname) {
        return Users.builder()
                .nickname(nickname)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public static Users ofEmail(String email) {
        return Users.builder()
                .nickname(DEFAULT_NICKNAME)
                .email(email)
                .build();
    }

    public static Users of() {
        return Users.builder()
                .nickname(DEFAULT_NICKNAME)
                .email(DEFAULT_EMAIL)
                .build();
    }

}
