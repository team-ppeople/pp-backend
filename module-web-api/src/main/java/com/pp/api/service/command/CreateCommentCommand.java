package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentCommand(
        @NotNull(message = "게시글 id가 없어요")
        Long postId,
        @NotBlank(message = "내용이 없어요")
        String content
) {

}
