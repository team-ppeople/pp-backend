package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UploadFileNotExistsException extends BaseException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 파일이에요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public UploadFileNotExistsException() {
        this(DEFAULT_MESSAGE);
    }

    public UploadFileNotExistsException(String message) {
        super(
                STATUS,
                message
        );
    }

    public UploadFileNotExistsException(
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
