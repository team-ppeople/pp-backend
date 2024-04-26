package com.pp.api.configuration.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@RequiredArgsConstructor
public class CustomOauth2ResourceServerAccessDeniedHandler implements AccessDeniedHandler {

    private static final HttpStatus RESPONSE_STATUS = FORBIDDEN;

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(
                RESPONSE_STATUS,
                RESPONSE_STATUS.getReasonPhrase()
        );

        body.setInstance(URI.create(request.getRequestURI()));

        response.setContentType(APPLICATION_PROBLEM_JSON_VALUE);

        response.setStatus(RESPONSE_STATUS.value());

        response.getWriter()
                .write(objectMapper.writeValueAsString(body));
    }

}
