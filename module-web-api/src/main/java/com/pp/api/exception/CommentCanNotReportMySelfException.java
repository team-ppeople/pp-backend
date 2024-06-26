package com.pp.api.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CommentCanNotReportMySelfException extends BaseException {

    private static final String DEFAULT_MESSAGE = "본인이 작성한 댓글은 신고할 수 없어요";

    private static final HttpStatus STATUS = BAD_REQUEST;

    public CommentCanNotReportMySelfException() {
        this(DEFAULT_MESSAGE);
    }

    public CommentCanNotReportMySelfException(String message) {
        super(
                STATUS,
                message
        );
    }

    public CommentCanNotReportMySelfException(
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
