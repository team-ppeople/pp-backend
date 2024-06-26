package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UploadFileTypeNotMatchedException extends BaseException {

    private static final String DEFAULT_MESSAGE = "사용할 수 없는 이미지 유형이에요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public UploadFileTypeNotMatchedException() {
        this(DEFAULT_MESSAGE);
    }

    public UploadFileTypeNotMatchedException(String message) {
        super(
                STATUS,
                message
        );
    }

    public UploadFileTypeNotMatchedException(
            String message,
            Throwable cause
    ) {
        super(
                STATUS,
                message,
                cause
        );
    }

}
