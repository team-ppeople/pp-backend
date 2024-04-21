package com.pp.api.service.command;

import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SaveOauthUserTokenCommand extends CommandSelfValidator<SaveOauthUserTokenCommand> {

    @NotNull(message = "Oauth 유저 클라이언트 값이 없습니다.")
    private final OauthUserClient client;

    @NotBlank(message = "subject 값이 없습니다.")
    private final String subject;

    @NotBlank(message = "accessToken 값이 없습니다.")
    private final String accessToken;

    @NotNull(message = "expiresIn 값이 없습니다.")
    private final Integer expiresIn;

    @NotBlank(message = "refreshToken 값이 없습니다.")
    private final String refreshToken;

    private SaveOauthUserTokenCommand(
            OauthUserClient client,
            String subject,
            String accessToken,
            Integer expiresIn,
            String refreshToken
    ) {
        this.client = client;
        this.subject = subject;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.validate();
    }

    public static SaveOauthUserTokenCommand of(
            OauthUserClient client,
            String subject,
            String accessToken,
            Integer expiresIn,
            String refreshToken
    ) {
        return new SaveOauthUserTokenCommand(
                client,
                subject,
                accessToken,
                expiresIn,
                refreshToken
        );
    }

}
