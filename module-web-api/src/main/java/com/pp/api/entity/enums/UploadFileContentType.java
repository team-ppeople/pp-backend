package com.pp.api.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UploadFileContentType {

    IMAGE_PNG("image/png", "png"),
    IMAGE_GIF("image/gif", "gif"),
    IMAGE_JPEG("image/jpeg", "jpeg");

    @JsonValue
    private final String type;

    private final String value;

    @JsonCreator
    public static UploadFileContentType fromType(String type) {
        return Arrays.stream(values())
                .filter(uploadFileContentType -> uploadFileContentType.getType().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
