package com.pp.api.controller.dto;

import java.util.List;

public record PresignedUploadUrlResponse(
        List<PresignedUploadFile> presignedUploadFiles
) {

}
