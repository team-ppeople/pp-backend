package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class EnumTypeNotPresentException extends BaseException {

    private static final HttpStatus STATUS = BAD_REQUEST;

    public EnumTypeNotPresentException(String message) {
        super(
                STATUS,
                message
        );
    }

    public EnumTypeNotPresentException(
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
