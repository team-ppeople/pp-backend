package com.pp.api.exception;

import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@Getter
public class BaseException extends NestedRuntimeException implements ErrorResponse {

    private final HttpStatusCode statusCode;

    private final ProblemDetail body;

    public BaseException(
            HttpStatusCode statusCode,
            String message
    ) {
        super(message);

        this.statusCode = statusCode;
        this.body = forStatusAndDetail(
                statusCode,
                message
        );
    }

    public BaseException(
            HttpStatusCode statusCode,
            String message,
            Throwable cause
    ) {
        super(
                message,
                cause
        );

        this.statusCode = statusCode;
        this.body = forStatusAndDetail(
                statusCode,
                message
        );
    }

}
