package com.pp.api.service.command;

import com.pp.api.controller.dto.IsRegisteredOauthUserResponse;
import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import static com.pp.api.entity.enums.OauthUserClient.APPLE;
import static org.springframework.util.StringUtils.hasText;

@Getter
public class RegisterOauthUserCommand extends CommandSelfValidator<IsRegisteredOauthUserResponse> {

    @NotNull(message = "Oauth 유저 클라이언트 값이 없습니다.")
    private final OauthUserClient client;

    @NotNull(message = "subject 값이 없습니다.")
    private final String subject;

    @NotNull(message = "nickname 값이 없습니다.")
    private final String nickname;

    @NotNull(message = "email 값이 없습니다.")
    private final String email;

    private final String authorizationCode;

    private RegisterOauthUserCommand(
            OauthUserClient client,
            String subject,
            String nickname,
            String email,
            String authorizationCode
    ) {
        this.client = client;
        this.subject = subject;
        this.nickname = nickname;
        this.email = email;
        this.authorizationCode = authorizationCode;
        this.validate();
    }

    public static RegisterOauthUserCommand of(
            OauthUserClient client,
            String subject,
            String nickname,
            String email,
            String authorizationCode
    ) {
        return new RegisterOauthUserCommand(
                client,
                subject,
                nickname,
                email,
                authorizationCode
        );
    }

    @AssertTrue(message = "authorizationCode 값이 없습니다.")
    public boolean isAuthorizationCodeValidate() {
        if (this.client == APPLE && !hasText(this.authorizationCode)) {
            return false;
        }

        return true;
    }

}
