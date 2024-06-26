package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PostAlreadyReportedException extends BaseException {

    private static final String DEFAULT_MESSAGE = "이미 신고한 게시글이에요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public PostAlreadyReportedException() {
        this(DEFAULT_MESSAGE);
    }

    public PostAlreadyReportedException(String message) {
        super(
                STATUS,
                message
        );
    }

    public PostAlreadyReportedException(
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
