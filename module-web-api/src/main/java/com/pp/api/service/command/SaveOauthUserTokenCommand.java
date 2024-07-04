package com.pp.api.service.command;

import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveOauthUserTokenCommand(
        @NotNull(message = "Oauth 유저 클라이언트 값이 없어요")
        OauthUserClient client,
        @NotBlank(message = "subject 값이 없어요")
        String subject,
        @NotBlank(message = "accessToken 값이 없어요")
        String accessToken,
        @NotNull(message = "expiresIn 값이 없어요")
        Integer expiresIn,
        @NotBlank(message = "refreshToken 값이 없어요")
        String refreshToken
) {
}
