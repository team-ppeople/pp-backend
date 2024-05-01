package com.pp.api.exception;

import lombok.Getter;
import org.springframework.http.ProblemDetail;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
public class NotPermittedUserAccessException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "권한이 없는 유저입니다.";

    private final ProblemDetail body;

    private NotPermittedUserAccessException(String message) {
        super(
                FORBIDDEN,
                message
        );

        this.body = ProblemDetail.forStatusAndDetail(
                this.getStatusCode(),
                message
        );
    }

    private NotPermittedUserAccessException(
            String message,
            Throwable cause
    ) {
        super(
                FORBIDDEN,
                message,
                cause
        );

        this.body = ProblemDetail.forStatusAndDetail(
                this.getStatusCode(),
                message
        );
    }

    public static NotPermittedUserAccessException ofDefaultMessage() {
        return new NotPermittedUserAccessException(DEFAULT_ERROR_MESSAGE);
    }

    public static NotPermittedUserAccessException of(String message) {
        return new NotPermittedUserAccessException(message);
    }

    public static NotPermittedUserAccessException of(Throwable cause) {
        return new NotPermittedUserAccessException(
                DEFAULT_ERROR_MESSAGE,
                cause
        );
    }

    public static NotPermittedUserAccessException from(
            String message,
            Throwable cause
    ) {
        return new NotPermittedUserAccessException(
                message,
                cause
        );
    }

}
