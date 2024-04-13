package com.pp.api.entity.enums;

import lombok.Getter;

@Getter
public enum OauthUserClient {

    KAKAO,
    APPLE;

    public static OauthUserClient valueOfIgnoreCase(String value) {
        return valueOf(value.toUpperCase());
    }

}
