package com.pp.api.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CannotAccessBlockUserException extends BaseException {

    private static final String ACCESS_USER_ERROR_MESSAGE = "차단한 유저에요";

    private static final String ACCESS_POST_ERROR_MESSAGE = "차단한 유저의 게시물이에요";

    public CannotAccessBlockUserException(String message) {
        super(
                BAD_REQUEST,
                message
        );
    }

    public static CannotAccessBlockUserException ofAccessUserMessage() {
        return new CannotAccessBlockUserException(ACCESS_USER_ERROR_MESSAGE);
    }

    public static CannotAccessBlockUserException ofAccessPostMessage() {
        return new CannotAccessBlockUserException(ACCESS_POST_ERROR_MESSAGE);
    }

}
