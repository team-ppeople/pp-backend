package com.pp.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import java.io.IOException;

import static jakarta.servlet.DispatcherType.REQUEST;
import static java.util.UUID.randomUUID;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Slf4j
@Order(value = HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class MDCLoggingFilter implements Filter {

    public static final int ORDER = HIGHEST_PRECEDENCE;

    public static final String TRACE_ID_KEY = "traceId";

    public static final String REQUEST_URI_KEY = "requestURI";

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        if (servletRequest.getDispatcherType() == REQUEST) {
            MDC.put(
                    TRACE_ID_KEY,
                    randomUUID().toString()
            );

            MDC.put(
                    REQUEST_URI_KEY,
                    resolveRequestURI(servletRequest)
            );
        }

        boolean isNeedForwardToErrorController = false;

        try {
            filterChain.doFilter(
                    servletRequest,
                    servletResponse
            );
        } catch (Exception exception) {
            isNeedForwardToErrorController = true;

            throw exception;
        } finally {
            if (!isNeedForwardToErrorController) {
                MDC.clear();
            }
        }
    }

    private String resolveRequestURI(ServletRequest servletRequest) {
        if (servletRequest instanceof HttpServletRequest httpServletRequest) {
            return httpServletRequest.getRequestURI();
        }

        return null;
    }

}
