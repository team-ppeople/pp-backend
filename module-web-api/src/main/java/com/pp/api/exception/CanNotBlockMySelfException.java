package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CanNotBlockMySelfException extends BaseException {

    private static final String DEFAULT_MESSAGE = "자기 자신은 차단 할 수 없어요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public CanNotBlockMySelfException() {
        this(DEFAULT_MESSAGE);
    }

    public CanNotBlockMySelfException(String message) {
        super(
                STATUS,
                message
        );
    }

    public CanNotBlockMySelfException(
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
