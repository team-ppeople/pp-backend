package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentCommand extends CommandSelfValidator<CreateCommentCommand> {

    @NotBlank(message = "내용이 없습니다.")
    private final String content;

    private CreateCommentCommand(String content) {
        this.content = content;
        this.validate();
    }

    public static CreateCommentCommand of(String content) {
        return new CreateCommentCommand(content);
    }

}
