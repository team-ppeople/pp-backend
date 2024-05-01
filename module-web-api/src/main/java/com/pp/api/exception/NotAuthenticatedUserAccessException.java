package com.pp.api.exception;

import lombok.Getter;
import org.springframework.http.ProblemDetail;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public class NotAuthenticatedUserAccessException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "로그인하지 않은 유저입니다.";

    private final ProblemDetail body;

    private NotAuthenticatedUserAccessException(String message) {
        super(
                UNAUTHORIZED,
                message
        );

        this.body = ProblemDetail.forStatusAndDetail(
                this.getStatusCode(),
                message
        );
    }

    private NotAuthenticatedUserAccessException(
            String message,
            Throwable cause
    ) {
        super(
                UNAUTHORIZED,
                message,
                cause
        );

        this.body = ProblemDetail.forStatusAndDetail(
                this.getStatusCode(),
                message
        );
    }

    public static NotAuthenticatedUserAccessException ofDefaultMessage() {
        return new NotAuthenticatedUserAccessException(DEFAULT_ERROR_MESSAGE);
    }

    public static NotAuthenticatedUserAccessException of(String message) {
        return new NotAuthenticatedUserAccessException(message);
    }

    public static NotAuthenticatedUserAccessException of(Throwable cause) {
        return new NotAuthenticatedUserAccessException(
                DEFAULT_ERROR_MESSAGE,
                cause
        );
    }

    public static NotAuthenticatedUserAccessException from(
            String message,
            Throwable cause
    ) {
        return new NotAuthenticatedUserAccessException(
                message,
                cause
        );
    }

}
