package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class NotPermittedUserAccessException extends BaseException {

    private static final String DEFAULT_MESSAGE = "권한이 없는 유저에요";

    private static final HttpStatus STATUS = FORBIDDEN;

    public NotPermittedUserAccessException() {
        this(DEFAULT_MESSAGE);
    }

    public NotPermittedUserAccessException(String message) {
        super(
                STATUS,
                message
        );
    }

    public NotPermittedUserAccessException(
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
