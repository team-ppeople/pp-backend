package com.pp.api.service.command;

import com.pp.api.controller.dto.IsRegisteredOauthUserResponse;
import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterOauthUserCommand extends CommandSelfValidator<IsRegisteredOauthUserResponse> {

    @NotNull(message = "Oauth 유저 클라이언트 값이 없습니다.")
    private final OauthUserClient client;

    @NotBlank(message = "subject 값이 없습니다.")
    private final String subject;

    @NotBlank(message = "nickname 값이 없습니다.")
    private final String nickname;

    @NotBlank(message = "email 값이 없습니다.")
    private final String email;

    private RegisterOauthUserCommand(
            OauthUserClient client,
            String subject,
            String nickname,
            String email
    ) {
        this.client = client;
        this.subject = subject;
        this.nickname = nickname;
        this.email = email;
        this.validate();
    }

    public static RegisterOauthUserCommand of(
            OauthUserClient client,
            String subject,
            String nickname,
            String email
    ) {
        return new RegisterOauthUserCommand(
                client,
                subject,
                nickname,
                email
        );
    }

}
