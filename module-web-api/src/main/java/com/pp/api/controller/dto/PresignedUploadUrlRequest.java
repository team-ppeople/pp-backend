package com.pp.api.controller.dto;

import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PresignedUploadUrlRequest(

        @NotNull(message = "파일 확장자는 필수입니다.")
        UploadFileContentType fileContentType,

        @NotBlank(message = "파일이름은 필수입니다.")
        String fileName,

        @Min(value = 1, message = "파일 사이즈는 0보다 커야합니다.")
        long fileContentLength,

        @NotNull(message = "파일 업로드 타입은 필수입니다.")
        UploadFileType fileType
) {

        public String toFileKeyObjectPath() {
                return fileType.name().toLowerCase() + "/";
        }

}
