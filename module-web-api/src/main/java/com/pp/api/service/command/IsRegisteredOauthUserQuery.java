package com.pp.api.service.command;

import com.pp.api.controller.validator.EnumMatch;
import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.constraints.NotBlank;

public record IsRegisteredOauthUserQuery(
        @EnumMatch(
                message = "지원하지 않는 로그인 방식이에요",
                enumClass = OauthUserClient.class
        )
        @NotBlank(message = "클라이언트 값이 없어요")
        String client,
        @NotBlank(message = "idToken 값이 없어요")
        String idToken
) {

    public OauthUserClient getOauthUserClient() {
        return OauthUserClient.valueOfIgnoreCase(this.client);
    }

}
