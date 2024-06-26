package com.pp.api.controller;

import com.pp.api.client.slack.SlackClient;
import com.pp.api.client.slack.dto.SlackSendErrorMessageRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;

import static com.pp.api.filter.PersistenceLoggingFilter.REQUEST_URI_KEY;
import static com.pp.api.filter.PersistenceLoggingFilter.TRACE_ID_KEY;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.internalServerError;

@Controller
@RequestMapping(path = {"${server.error.path:${error.path:/error}}"})
public class CustomErrorController extends AbstractErrorController {

    private static final String SERVLET_FORWARD_REQUEST_URI_ATTRIBUTE_NAME = "jakarta.servlet.forward.request_uri";

    private static final String SERVLET_ERROR_EXCEPTION_ATTRIBUTE_NAME = "jakarta.servlet.error.exception";

    private final MessageSource messageSource;

    private final SlackClient slackClient;

    public CustomErrorController(
            MessageSource messageSource,
            ErrorAttributes errorAttributes,
            List<ErrorViewResolver> errorViewResolvers,
            SlackClient slackClient
    ) {
        super(errorAttributes, errorViewResolvers);
        this.messageSource = messageSource;
        this.slackClient = slackClient;
    }

    @RequestMapping
    public ResponseEntity<?> error(HttpServletRequest request) {
        Exception exception = (Exception) request.getAttribute(SERVLET_ERROR_EXCEPTION_ATTRIBUTE_NAME);

        sendErrorAlertMessage(exception);

        ProblemDetail body = this.createProblemDetail(
                exception,
                INTERNAL_SERVER_ERROR,
                "서비스에 문제가 발생했어요. 잠시 후 다시 시도해 주세요",
                null,
                null
        );

        body.setInstance(URI.create((String) request.getAttribute(SERVLET_FORWARD_REQUEST_URI_ATTRIBUTE_NAME)));

        return internalServerError().body(body);
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

    private void sendErrorAlertMessage(Exception exception) {
        SlackSendErrorMessageRequest request = new SlackSendErrorMessageRequest(
                MDC.get(REQUEST_URI_KEY),
                MDC.get(TRACE_ID_KEY),
                exception
        );

        slackClient.sendErrorMessage(request);
    }

}
