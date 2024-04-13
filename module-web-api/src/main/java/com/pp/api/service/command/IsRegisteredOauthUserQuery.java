package com.pp.api.service.command;

import com.pp.api.controller.dto.IsRegisteredOauthUserResponse;
import com.pp.api.controller.validator.AllowedOauthUserClient;
import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class IsRegisteredOauthUserQuery extends CommandSelfValidator<IsRegisteredOauthUserResponse> {

    @AllowedOauthUserClient
    @NotNull(message = "Oauth 유저 클라이언트 값이 없습니다.")
    private final String client;

    @NotNull(message = "idToken 값이 없습니다.")
    private final String idToken;

    private IsRegisteredOauthUserQuery(
            String client,
            String idToken
    ) {
        this.client = client;
        this.idToken = idToken;
        this.validate();
    }

    public static IsRegisteredOauthUserQuery of(
            String client,
            String idToken
    ) {
        return new IsRegisteredOauthUserQuery(
                client,
                idToken
        );
    }

    public OauthUserClient getOauthUserClient() {
        return OauthUserClient.valueOfIgnoreCase(this.client);
    }

}
