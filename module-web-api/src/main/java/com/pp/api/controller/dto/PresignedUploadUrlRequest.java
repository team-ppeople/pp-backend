package com.pp.api.controller.dto;

import com.pp.api.entity.enums.UploadFileContentTypes;
import com.pp.api.entity.enums.UploadFileTypes;

public record PresignedUploadUrlRequest(
        UploadFileContentTypes uploadFileContentTypes,
        String fileName,
        int fileContentLength,
        UploadFileTypes uploadFileTypes
) {

}
