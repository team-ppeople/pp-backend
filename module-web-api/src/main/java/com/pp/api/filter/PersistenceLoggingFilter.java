package com.pp.api.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.api.service.RequestResponseLoggingService;
import com.pp.api.service.command.LoggingRequestResponseCommand;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.UUID.randomUUID;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.util.StreamUtils.copyToString;

@Slf4j
@Order(value = HIGHEST_PRECEDENCE)
@Component
@RequiredArgsConstructor
public class PersistenceLoggingFilter extends OncePerRequestFilter {

    public static final String TRACE_ID_KEY = "traceId";

    public static final String REQUEST_URI_KEY = "requestURI";

    private final RequestResponseLoggingService requestResponseLoggingService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        LocalDateTime requestReceivedTime = LocalDateTime.now();

        MDC.put(
                TRACE_ID_KEY,
                randomUUID().toString()
        );

        MDC.put(
                REQUEST_URI_KEY,
                request.getRequestURI()
        );

        HttpServletRequest requestWrapper = wrapByMultipleReadableRequestWrapper(request);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            doFilter(
                    requestWrapper,
                    responseWrapper,
                    filterChain
            );
        } finally {
            loggingRequestResponse(
                    requestWrapper,
                    responseWrapper,
                    requestReceivedTime
            );

            responseWrapper.copyBodyToResponse();

            MDC.clear();
        }
    }

    private HttpServletRequest wrapByMultipleReadableRequestWrapper(HttpServletRequest request) throws IOException {
        if (isSkipWrap(request)) {
            return request;
        }

        return new MultipleReadableRequestWrapper(request);
    }

    private boolean isSkipWrap(HttpServletRequest request) {
        if (request.getContentType() == null) {
            return true;
        }

        if (request.getContentType().equals(APPLICATION_FORM_URLENCODED_VALUE)) {
            return true;
        }

        return request.getContentType().startsWith(MULTIPART_FORM_DATA_VALUE);
    }

    private void loggingRequestResponse(
            HttpServletRequest request,
            ContentCachingResponseWrapper responseWrapper,
            LocalDateTime requestReceivedTime
    ) throws IOException {
        LoggingRequestResponseCommand command = new LoggingRequestResponseCommand(
                MDC.get(TRACE_ID_KEY),
                readRequestURI(request),
                readHttpMethod(request),
                readHttpStatus(responseWrapper),
                readRequestHeader(request),
                readRequestPayload(request),
                readResponseHeader(responseWrapper),
                readResponsePayload(responseWrapper),
                requestReceivedTime,
                LocalDateTime.now()
        );

        requestResponseLoggingService.loggingAsync(command);
    }

    private String readRequestURI(HttpServletRequest request) {
        if (request.getQueryString() == null) {
            return request.getRequestURI();
        }

        return request.getRequestURI() + "?" + request.getQueryString();
    }

    private HttpMethod readHttpMethod(HttpServletRequest request) {
        return HttpMethod.valueOf(request.getMethod());
    }

    private HttpStatus readHttpStatus(ContentCachingResponseWrapper responseWrapper) {
        return HttpStatus.valueOf(responseWrapper.getStatus());
    }

    private String readRequestHeader(HttpServletRequest request) throws JsonProcessingException {
        MultiValueMap<String, Object> requestHeaders = new LinkedMultiValueMap<>();

        request.getHeaderNames()
                .asIterator()
                .forEachRemaining(headerName ->
                        request.getHeaders(headerName)
                                .asIterator()
                                .forEachRemaining(headerValue ->
                                        requestHeaders.add(headerName, headerValue)
                                )
                );

        return objectMapper.writeValueAsString(requestHeaders);
    }

    private String readRequestPayload(HttpServletRequest request) throws IOException {
        if (request.getContentType() == null) {
            return null;
        }

        if (request instanceof MultipleReadableRequestWrapper) {
            return copyToString(
                    request.getInputStream(),
                    UTF_8
            );
        }

        return objectMapper.writeValueAsString(request.getParameterMap());
    }

    private String readResponsePayload(ContentCachingResponseWrapper responseWrapper) throws IOException {
        return copyToString(
                responseWrapper.getContentInputStream(),
                UTF_8
        );
    }

    private String readResponseHeader(ContentCachingResponseWrapper responseWrapper) throws JsonProcessingException {
        MultiValueMap<String, Object> responseHeaders = new LinkedMultiValueMap<>();

        responseWrapper.getHeaderNames()
                .forEach(headerName ->
                        responseWrapper.getHeaders(headerName)
                                .forEach(headerValue ->
                                        responseHeaders.add(headerName, headerValue)
                                )
                );

        return objectMapper.writeValueAsString(responseHeaders);
    }

}
