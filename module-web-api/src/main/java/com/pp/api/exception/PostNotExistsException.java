package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PostNotExistsException extends BaseException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 게시글이에요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public PostNotExistsException() {
        this(DEFAULT_MESSAGE);
    }

    public PostNotExistsException(String message) {
        super(
                STATUS,
                message
        );
    }

    public PostNotExistsException(
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
