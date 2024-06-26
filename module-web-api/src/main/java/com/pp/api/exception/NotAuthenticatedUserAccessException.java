package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class NotAuthenticatedUserAccessException extends BaseException {

    private static final String DEFAULT_MESSAGE = "로그인하지 않은 유저에요";

    private static final HttpStatus STATUS = UNAUTHORIZED;

    public NotAuthenticatedUserAccessException() {
        this(DEFAULT_MESSAGE);
    }

    public NotAuthenticatedUserAccessException(String message) {
        super(
                STATUS,
                message
        );
    }

    public NotAuthenticatedUserAccessException(
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
