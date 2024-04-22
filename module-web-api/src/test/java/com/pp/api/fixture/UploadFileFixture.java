package com.pp.api.fixture;

import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.entity.enums.UploadFileContentTypes;
import com.pp.api.entity.enums.UploadFileTypes;

import static com.pp.api.entity.enums.UploadFileContentTypes.IMAGE_JPEG;
import static com.pp.api.entity.enums.UploadFileTypes.POST_IMAGE;
import static com.pp.api.entity.enums.UploadFileTypes.PROFILE_IMAGE;

public class UploadFileFixture {

    private static final String DEFAULT_URL = "https://avatars.githubusercontent.com/u/52724515";

    private static final UploadFileContentTypes DEFAULT_CONTENT_TYPE = IMAGE_JPEG;

    private static final Long DEFAULT_CONTENT_LENGTH = 1048576L;


    public static UploadFiles from(
            UploadFileTypes fileType,
            String url,
            UploadFileContentTypes contentType,
            Long contentLength,
            Users uploader
    ) {
        return UploadFiles.builder()
                .fileType(fileType)
                .url(url)
                .contentType(contentType)
                .contentLength(contentLength)
                .uploader(uploader)
                .build();
    }

    public static UploadFiles fromFileTypeAndContentType(
            UploadFileTypes fileType,
            UploadFileContentTypes contentType,
            Users uploader
    ) {
        return UploadFiles.builder()
                .fileType(fileType)
                .url(DEFAULT_URL)
                .contentType(contentType)
                .contentLength(DEFAULT_CONTENT_LENGTH)
                .uploader(uploader)
                .build();
    }

    public static UploadFiles postImageFileOfUploader(Users uploader) {
        return UploadFiles.builder()
                .fileType(POST_IMAGE)
                .url(DEFAULT_URL)
                .contentType(DEFAULT_CONTENT_TYPE)
                .contentLength(DEFAULT_CONTENT_LENGTH)
                .uploader(uploader)
                .build();
    }

    public static UploadFiles profileImageFileOfUploader(Users uploader) {
        return UploadFiles.builder()
                .fileType(PROFILE_IMAGE)
                .url(DEFAULT_URL)
                .contentType(DEFAULT_CONTENT_TYPE)
                .contentLength(DEFAULT_CONTENT_LENGTH)
                .uploader(uploader)
                .build();
    }

}
