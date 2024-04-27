package com.pp.api.controller.dto;

import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PresignedUploadUrlRequest(

        UploadFileContentType uploadFileContentTypes,

        @NotBlank(message = "파일이름은 필수입니다.")
        String fileName,

        @Min(1)
        long fileContentLength,

        UploadFileType uploadFileTypes
) {

        public String toFileKeyObjectPath() {
                return uploadFileTypes.name().toLowerCase() + "/";
        }

}
