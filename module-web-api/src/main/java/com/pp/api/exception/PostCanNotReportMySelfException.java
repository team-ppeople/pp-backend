package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PostCanNotReportMySelfException extends BaseException {

    private static final String DEFAULT_MESSAGE = "본인이 작성한 게시글은 신고할 수 없어요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public PostCanNotReportMySelfException() {
        this(DEFAULT_MESSAGE);
    }

    public PostCanNotReportMySelfException(String message) {
        super(
                STATUS,
                message
        );
    }

    public PostCanNotReportMySelfException(
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
