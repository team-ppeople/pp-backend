package com.pp.api.controller.dto;

import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PresignedUploadUrlRequest(

        @NotNull(message = "파일 확장자는 필수에요")
        UploadFileContentType fileContentType,

        @NotBlank(message = "파일 이름은 필수에요")
        String fileName,

        @Min(value = 1, message = "파일 사이즈는 0보다 커야해요")
        @Max(value = 1048576, message = "파일 사이즈는 1048576(byte)를 초과할 수 없어요")
        long fileContentLength,

        @NotNull(message = "파일 업로드 타입은 필수에요")
        UploadFileType fileType
) {

        public String toFileKeyObjectPath() {
                return fileType.name().toLowerCase() + "/";
        }

        public String appendObjectKey(String name) {
                return toFileKeyObjectPath() + name + "." + fileContentType.getValue();
        }

}
