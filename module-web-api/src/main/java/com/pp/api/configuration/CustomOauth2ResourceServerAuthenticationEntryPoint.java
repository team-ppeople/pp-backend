package com.pp.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@RequiredArgsConstructor
public class CustomOauth2ResourceServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final HttpStatus RESPONSE_STATUS = UNAUTHORIZED;

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
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
