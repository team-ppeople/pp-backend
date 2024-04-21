package com.pp.api.service.command;

import lombok.Getter;

@Getter
public class UpdateUserCommand extends CommandSelfValidator<UpdateUserCommand> {

    private final String nickname;

    private final Long profileImageFileUploadId;

    private UpdateUserCommand(
            String nickname,
            Long profileImageFileUploadId
    ) {
        this.nickname = nickname;
        this.profileImageFileUploadId = profileImageFileUploadId;
        this.validate();
    }

    public static UpdateUserCommand of(
            String nickname,
            Long profileImageFileUploadId
    ) {
        return new UpdateUserCommand(
                nickname,
                profileImageFileUploadId
        );
    }

}
