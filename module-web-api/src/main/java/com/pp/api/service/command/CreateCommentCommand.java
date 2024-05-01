package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentCommand extends CommandSelfValidator<CreateCommentCommand> {

    @NotNull(message = "게시글 id가 없습니다.")
    private final Long postId;

    @NotBlank(message = "내용이 없습니다.")
    private final String content;

    public CreateCommentCommand(
            Long postId,
            String content
    ) {
        this.postId = postId;
        this.content = content;
        this.validate();
    }

}
