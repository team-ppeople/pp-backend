package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CommentAlreadyReportedException extends BaseException {

    private static final String DEFAULT_MESSAGE = "이미 신고한 댓글이에요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public CommentAlreadyReportedException() {
        this(DEFAULT_MESSAGE);
    }

    public CommentAlreadyReportedException(String message) {
        super(
                STATUS,
                message
        );
    }

    public CommentAlreadyReportedException(
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
