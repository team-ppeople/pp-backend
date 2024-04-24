package com.pp.api.fixture;

import com.pp.api.entity.User;

public class UserFixture {

    private static final String DEFAULT_NICKNAME = "sinbom";

    private static final String DEFAULT_EMAIL = "dev.sinbom@gmail.com";

    public static User from(
            String nickname,
            String email
    ) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .build();
    }

    public static User ofNickname(String nickname) {
        return User.builder()
                .nickname(nickname)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public static User ofEmail(String email) {
        return User.builder()
                .nickname(DEFAULT_NICKNAME)
                .email(email)
                .build();
    }

    public static User of() {
        return User.builder()
                .nickname(DEFAULT_NICKNAME)
                .email(DEFAULT_EMAIL)
                .build();
    }

}
