package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CreatePostCommand extends CommandSelfValidator<CreatePostCommand> {

    @NotBlank(message = "제목이 없습니다.")
    private final String title;

    @NotBlank(message = "내용이 없습니다.")
    private final String content;

    @Size(min = 1, max = 10, message = "이미지는 허용 개숫는 최소 {min}개 최대 {max}개 입니다.")
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
