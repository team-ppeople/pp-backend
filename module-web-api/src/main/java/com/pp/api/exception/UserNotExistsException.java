package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserNotExistsException extends BaseException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 사용자에요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public UserNotExistsException() {
        this(DEFAULT_MESSAGE);
    }

    private UserNotExistsException(String message) {
        super(
                STATUS,
                message
        );
    }

    private UserNotExistsException(
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
