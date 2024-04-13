package com.pp.api.event.handler;

import com.pp.api.entity.enums.OauthUserClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class OauthUserRegisteredEvent {

    private final OauthUserClient oauthUserClient;

    private final String authorizationCode;

    private final Long oauthUserId;

    public static OauthUserRegisteredEvent of(
            OauthUserClient oauthUserClient,
            String authorizationCode,
            Long oauthUserId
    ) {
        return new OauthUserRegisteredEvent(
                oauthUserClient,
                authorizationCode,
                oauthUserId
        );
    }

}
