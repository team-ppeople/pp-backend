package com.pp.api.exception.handler;

import com.pp.api.client.slack.SlackClient;
import com.pp.api.client.slack.dto.SlackSendErrorMessageRequest;
import com.pp.api.exception.BaseException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.pp.api.filter.MDCLoggingFilter.REQUEST_URI_KEY;
import static com.pp.api.filter.MDCLoggingFilter.TRACE_ID_KEY;
import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final SlackClient slackClient;

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

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ex.getAllErrors()
                .stream()
                .findFirst()
                .ifPresent(error -> ex.getBody().setDetail(error.getDefaultMessage()));

        return super.handleHandlerMethodValidationException(
                ex,
                headers,
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleHttpRequestMethodNotSupported(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleHttpMediaTypeNotSupported(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleHttpMediaTypeNotAcceptable(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleMissingPathVariable(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleMissingServletRequestParameter(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleMissingServletRequestPart(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleServletRequestBindingException(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleNoHandlerFoundException(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleNoResourceFoundException(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleAsyncRequestTimeoutException(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(
            ErrorResponseException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleErrorResponseException(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleMaxUploadSizeExceededException(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(
            ConversionNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleConversionNotSupported(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleTypeMismatch(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleHttpMessageNotReadable(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleHttpMessageNotWritable(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodValidationException(
            MethodValidationException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return rewriteProblemDetailToSimpleMessage(
                super.handleMethodValidationException(
                        ex,
                        headers,
                        status,
                        request
                ),
                status
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
                "접근 권한이 없어요",
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

        sendErrorAlertMessage(ex);

        ProblemDetail body = this.createProblemDetail(
                ex,
                INTERNAL_SERVER_ERROR,
                "서비스에 문제가 발생했어요. 잠시 후 다시 시도해 주세요",
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

    private ResponseEntity<Object> rewriteProblemDetailToSimpleMessage(
            ResponseEntity<Object> errorResponseEntity,
            HttpStatusCode httpStatusCode
    ) {
        if (errorResponseEntity == null) {
            return null;
        }

        if (!httpStatusCode.isError()) {
            return errorResponseEntity;
        }

        if (!(errorResponseEntity.getBody() instanceof ProblemDetail problemDetail)) {
            return errorResponseEntity;
        }

        if (httpStatusCode.is4xxClientError()) {
            problemDetail.setDetail("잘못된 요청이에요");

            return errorResponseEntity;
        }

        problemDetail.setDetail("서비스에 문제가 발생했어요. 잠시 후 다시 시도해 주세요");

        return errorResponseEntity;
    }

    private void sendErrorAlertMessage(Exception exception) {
        SlackSendErrorMessageRequest request = new SlackSendErrorMessageRequest(
                MDC.get(REQUEST_URI_KEY),
                MDC.get(TRACE_ID_KEY),
                exception
        );

        slackClient.sendErrorMessage(request);
    }

}
