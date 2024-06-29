package com.pp.api.configuration.async.handler;

import com.pp.api.client.slack.SlackClient;
import com.pp.api.client.slack.dto.SlackSendErrorMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

import static com.pp.api.filter.MDCLoggingFilter.REQUEST_URI_KEY;
import static com.pp.api.filter.MDCLoggingFilter.TRACE_ID_KEY;

@Slf4j
@RequiredArgsConstructor
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final SlackClient slackClient;

    @Override
    public void handleUncaughtException(
            Throwable ex,
            Method method,
            Object... params
    ) {
        log.error(
                "async exception : {} handle",
                ex.getClass().getName(),
                ex
        );

        sendErrorAlertMessage(ex);
    }

    private void sendErrorAlertMessage(Throwable exception) {
        SlackSendErrorMessageRequest request = new SlackSendErrorMessageRequest(
                MDC.get(REQUEST_URI_KEY),
                MDC.get(TRACE_ID_KEY),
                exception
        );

        slackClient.sendErrorMessage(request);
    }

}
