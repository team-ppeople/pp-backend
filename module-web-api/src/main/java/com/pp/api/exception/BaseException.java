package com.pp.api.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class BaseException extends RuntimeException implements ErrorResponse {

    private final HttpStatusCode httpStatusCode;

    private final ProblemDetail body;

    public BaseException(
            HttpStatusCode httpStatusCode,
            String message
    ) {
        super(message);

        this.httpStatusCode = httpStatusCode;
        this.body = ProblemDetail.forStatusAndDetail(
                httpStatusCode,
                message
        );
    }

    public BaseException(
            HttpStatusCode httpStatusCode,
            String message,
            Throwable cause
    ) {
        super(
                message,
                cause
        );

        this.httpStatusCode = httpStatusCode;
        this.body = ProblemDetail.forStatusAndDetail(
                httpStatusCode,
                message
        );
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public ProblemDetail getBody() {
        return this.body;
    }

}
