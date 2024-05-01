package com.pp.api.service.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserCommand extends CommandSelfValidator<UpdateUserCommand> {

    @NotNull(message = "유저 id가 없습니다.")
    private final Long userId;

    private final String nickname;

    private final Long profileImageFileUploadId;

    public UpdateUserCommand(
            Long userId,
            String nickname,
            Long profileImageFileUploadId
    ) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageFileUploadId = profileImageFileUploadId;
        this.validate();
    }

}
