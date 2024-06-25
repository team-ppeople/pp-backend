package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CreatePostCommand extends CommandSelfValidator<CreatePostCommand> {

    @NotBlank(message = "제목이 없어요")
    private final String title;

    @NotBlank(message = "내용이 없어요")
    private final String content;

    @Size(max = 10, message = "이미지 허용 갯수는 최대 {max}개에요")
    private final List<Long> postImageFileUploadIds;

    public CreatePostCommand(
            String title,
            String content,
            List<Long> postImageFileUploadIds
    ) {
        this.title = title;
        this.content = content;
        this.postImageFileUploadIds = postImageFileUploadIds;
        this.validate();
    }

}
