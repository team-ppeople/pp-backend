package com.pp.api.service.command;

import jakarta.validation.constraints.NotNull;

public record UpdateUserCommand(
        @NotNull(message = "유저 id가 없어요")
        Long userId,
        String nickname,
        Long profileImageFileUploadId
) {

}
