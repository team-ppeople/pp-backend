package com.pp.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.*;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.ResponseEntity.internalServerError;

@Controller
@RequestMapping(path = {"${server.error.path:${error.path:/error}}"})
public class CustomErrorController extends AbstractErrorController {

    private static final String SERVLET_FORWARD_REQUEST_URI_ATTRIBUTE_NAME = "jakarta.servlet.forward.request_uri";

    private static final String SERVLET_ERROR_EXCEPTION_ATTRIBUTE_NAME = "jakarta.servlet.error.exception";

    private final MessageSource messageSource;

    private final ErrorProperties errorProperties;

    public CustomErrorController(
            MessageSource messageSource,
            ErrorAttributes errorAttributes,
            List<ErrorViewResolver> errorViewResolvers
    ) {
        super(errorAttributes, errorViewResolvers);
        this.messageSource = messageSource;
        this.errorProperties = new ErrorProperties();
    }

    @RequestMapping(produces = TEXT_HTML_VALUE)
    public ModelAndView errorHtml(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        HttpStatus status = this.getStatus(request);

        Map<String, Object> model = unmodifiableMap(
                this.getErrorAttributes(
                        request,
                        this.getErrorAttributeOptions(request)
                )
        );

        response.setStatus(status.value());

        ModelAndView modelAndView = this.resolveErrorView(
                request,
                response,
                status,
                model
        );

        return modelAndView != null ? modelAndView : new ModelAndView("error", model);
    }

    @RequestMapping
    public ResponseEntity<?> error(HttpServletRequest request) {
        ProblemDetail body = this.createProblemDetail(
                (Exception) request.getAttribute(SERVLET_ERROR_EXCEPTION_ATTRIBUTE_NAME),
                INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                null,
                null
        );

        body.setInstance(URI.create((String) request.getAttribute(SERVLET_FORWARD_REQUEST_URI_ATTRIBUTE_NAME)));

        return internalServerError().body(body);
    }

    private ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();

        if (this.errorProperties.isIncludeException()) {
            return options.including(EXCEPTION);
        }

        if (this.isIncludeStackTrace(request)) {
            return options.including(STACK_TRACE);
        }

        if (this.isIncludeMessage(request)) {
            return options.including(MESSAGE);
        }

        if (this.isIncludeBindingErrors(request)) {
            return options.including(BINDING_ERRORS);
        }

        return options;
    }

    private boolean isIncludeStackTrace(HttpServletRequest request) {
        return switch (this.getErrorProperties().getIncludeStacktrace()) {
            case ALWAYS -> true;
            case ON_PARAM -> this.getTraceParameter(request);
            default -> false;
        };
    }

    private boolean isIncludeMessage(HttpServletRequest request) {
        return switch (this.getErrorProperties().getIncludeMessage()) {
            case ALWAYS -> true;
            case ON_PARAM -> this.getMessageParameter(request);
            default -> false;
        };
    }

    private boolean isIncludeBindingErrors(HttpServletRequest request) {
        return switch (this.getErrorProperties().getIncludeBindingErrors()) {
            case ALWAYS -> true;
            case ON_PARAM -> this.getErrorsParameter(request);
            default -> false;
        };
    }

    private ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }

    protected ProblemDetail createProblemDetail(
            Exception ex,
            HttpStatusCode status,
            String defaultDetail,
            String detailMessageCode,
            Object[] detailMessageArguments
    ) {
        ErrorResponse.Builder builder = ErrorResponse.builder(
                ex,
                status,
                defaultDetail
        );

        if (detailMessageCode != null) {
            builder.detailMessageCode(detailMessageCode);
        }

        if (detailMessageArguments != null) {
            builder.detailMessageArguments(detailMessageArguments);
        }

        return builder.build()
                .updateAndGetBody(
                        this.messageSource,
                        getLocale()
                );
    }

}
