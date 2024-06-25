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

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RequiredArgsConstructor
public class CustomOauth2ResourceServerAccessDeniedHandler implements AccessDeniedHandler {

    private static final HttpStatus RESPONSE_STATUS = FORBIDDEN;

    private static final String RESPONSE_MESSAGE = "접근 권한이 없어요";

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        ProblemDetail body = forStatusAndDetail(
                RESPONSE_STATUS,
                RESPONSE_MESSAGE
        );

        body.setInstance(create(request.getRequestURI()));

        response.setContentType(APPLICATION_PROBLEM_JSON_VALUE);

        response.setCharacterEncoding("UTF-8");

        response.setStatus(RESPONSE_STATUS.value());

        response.getWriter()
                .write(objectMapper.writeValueAsString(body));
    }

}
