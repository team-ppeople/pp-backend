package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CommentNotExistsException extends BaseException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 댓글이에요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public CommentNotExistsException() {
        this(DEFAULT_MESSAGE);
    }

    public CommentNotExistsException(String message) {
        super(
                STATUS,
                message
        );
    }

    public CommentNotExistsException(
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
