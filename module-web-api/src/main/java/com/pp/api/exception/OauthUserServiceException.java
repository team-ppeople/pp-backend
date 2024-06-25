package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class OauthUserServiceException extends BaseException {

    private static final HttpStatus STATUS = BAD_REQUEST;

    public OauthUserServiceException(String message) {
        super(
                STATUS,
                message
        );
    }

    public OauthUserServiceException(
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
