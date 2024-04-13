package com.pp.api.entity.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OauthUserClient {

    KAKAO,
    APPLE;

    public static OauthUserClient valueOfIgnoreCase(String value) {
        return valueOf(value.toUpperCase());
    }

    public static boolean containsIgnoreCase(String value) {
        return Arrays.stream(values())
                .anyMatch(v -> v.name().equalsIgnoreCase(value));
    }

    public String parseClientSubject(String subject) {
        return this.name() + ":" + subject;
    }

}
