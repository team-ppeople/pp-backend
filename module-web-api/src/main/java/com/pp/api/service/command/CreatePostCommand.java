package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreatePostCommand(
        @NotBlank(message = "제목이 없어요")
        String title,
        @NotBlank(message = "내용이 없어요")
        String content,
        @Size(max = 10, message = "이미지 허용 갯수는 최대 {max}개에요")
        List<Long> postImageFileUploadIds
) {

}
