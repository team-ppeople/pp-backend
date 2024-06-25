package com.pp.api.configuration.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RequiredArgsConstructor
public class CustomOauth2ResourceServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final HttpStatus RESPONSE_STATUS = UNAUTHORIZED;

    private static final String RESPONSE_MESSAGE = "로그인이 유효하지 않아요";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
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
