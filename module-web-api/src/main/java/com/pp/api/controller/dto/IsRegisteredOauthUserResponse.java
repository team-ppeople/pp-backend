package com.pp.api.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class IsRegisteredOauthUserResponse {

    private final boolean isRegistered;

    public static IsRegisteredOauthUserResponse from(boolean isRegistered) {
        return new IsRegisteredOauthUserResponse(isRegistered);
    }

}
