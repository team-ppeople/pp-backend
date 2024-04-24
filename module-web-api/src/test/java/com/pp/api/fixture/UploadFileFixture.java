package com.pp.api.fixture;

import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;

import static com.pp.api.entity.enums.UploadFileContentType.IMAGE_JPEG;
import static com.pp.api.entity.enums.UploadFileType.POST_IMAGE;
import static com.pp.api.entity.enums.UploadFileType.PROFILE_IMAGE;

public class UploadFileFixture {

    private static final String DEFAULT_URL = "https://avatars.githubusercontent.com/u/52724515";

    private static final UploadFileContentType DEFAULT_CONTENT_TYPE = IMAGE_JPEG;

    private static final Long DEFAULT_CONTENT_LENGTH = 1048576L;


    public static UploadFile from(
            UploadFileType fileType,
            String url,
            UploadFileContentType contentType,
            Long contentLength,
            User uploader
    ) {
        return UploadFile.builder()
                .fileType(fileType)
                .url(url)
                .contentType(contentType)
                .contentLength(contentLength)
                .uploader(uploader)
                .build();
    }

    public static UploadFile fromFileTypeAndContentType(
            UploadFileType fileType,
            UploadFileContentType contentType,
            User uploader
    ) {
        return UploadFile.builder()
                .fileType(fileType)
                .url(DEFAULT_URL)
                .contentType(contentType)
                .contentLength(DEFAULT_CONTENT_LENGTH)
                .uploader(uploader)
                .build();
    }

    public static UploadFile postImageFileOfUploader(User uploader) {
        return UploadFile.builder()
                .fileType(POST_IMAGE)
                .url(DEFAULT_URL)
                .contentType(DEFAULT_CONTENT_TYPE)
                .contentLength(DEFAULT_CONTENT_LENGTH)
                .uploader(uploader)
                .build();
    }

    public static UploadFile profileImageFileOfUploader(User uploader) {
        return UploadFile.builder()
                .fileType(PROFILE_IMAGE)
                .url(DEFAULT_URL)
                .contentType(DEFAULT_CONTENT_TYPE)
                .contentLength(DEFAULT_CONTENT_LENGTH)
                .uploader(uploader)
                .build();
    }

}
