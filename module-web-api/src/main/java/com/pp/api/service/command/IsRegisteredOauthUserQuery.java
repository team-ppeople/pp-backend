package com.pp.api.service.command;

import com.pp.api.controller.validator.EnumMatch;
import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class IsRegisteredOauthUserQuery extends CommandSelfValidator<IsRegisteredOauthUserQuery> {

    @EnumMatch(
            message = "지원하지 않는 로그인 방식이에요",
            enumClass = OauthUserClient.class
    )
    @NotBlank(message = "클라이언트 값이 없어요")
    private final String client;

    @NotBlank(message = "idToken 값이 없어요")
    private final String idToken;

    public IsRegisteredOauthUserQuery(
            String client,
            String idToken
    ) {
        this.client = client;
        this.idToken = idToken;
        this.validate();
    }

    public OauthUserClient getOauthUserClient() {
        return OauthUserClient.valueOfIgnoreCase(this.client);
    }

}
