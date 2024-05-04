package com.pp.api.exception.handler;

import com.pp.api.exception.BaseException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ex.getFieldErrors()
                .stream()
                .findFirst()
                .ifPresent(fieldError -> ex.getBody().setDetail(fieldError.getDefaultMessage()));

        return super.handleMethodArgumentNotValid(
                ex,
                headers,
                status,
                request
        );
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handle(
            ConstraintViolationException ex,
            WebRequest request
    ) {
        ProblemDetail body = this.createProblemDetail(
                ex,
                BAD_REQUEST,
                BAD_REQUEST.getReasonPhrase(),
                null,
                null,
                request
        );

        ex.getConstraintViolations()
                .stream()
                .findFirst()
                .ifPresent(constraintViolation -> body.setDetail(constraintViolation.getMessage()));

        return super.handleExceptionInternal(
                ex,
                body,
                EMPTY,
                BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<?> handle(
            BaseException ex,
            WebRequest request
    ) {
        ProblemDetail body = ex.getBody();

        return super.handleExceptionInternal(
                ex,
                body,
                EMPTY,
                ex.getStatusCode(),
                request
        );
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> handle(
            AccessDeniedException ex,
            WebRequest request
    ) {
        ProblemDetail body = this.createProblemDetail(
                ex,
                FORBIDDEN,
                "접근 권한이 없습니다.",
                null,
                null,
                request
        );

        return super.handleExceptionInternal(
                ex,
                body,
                EMPTY,
                FORBIDDEN,
                request
        );
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> handle(
            IllegalArgumentException ex,
            WebRequest request
    ) {
        ProblemDetail body = this.createProblemDetail(
                ex,
                BAD_REQUEST,
                ex.getMessage(),
                null,
                null,
                request
        );

        return super.handleExceptionInternal(
                ex,
                body,
                EMPTY,
                BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handle(
            Exception ex,
            WebRequest request
    ) {
        log.error(
                "exception : {} handle",
                ex.getClass().getName(),
                ex
        );

        ProblemDetail body = this.createProblemDetail(
                ex,
                INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                null,
                null,
                request
        );

        return super.handleExceptionInternal(
                ex,
                body,
                EMPTY,
                INTERNAL_SERVER_ERROR,
                request
        );
    }

}
