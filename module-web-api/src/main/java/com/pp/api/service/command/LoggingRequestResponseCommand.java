package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public record LoggingRequestResponseCommand(
        @NotBlank(message = "요청 traceId가 없어요")
        String traceId,
        @NotBlank(message = "요청 url이 없어요")
        String url,
        @NotNull(message = "요청 method가 없어요")
        HttpMethod method,
        @NotNull(message = "응답 status가 없어요")
        HttpStatus status,
        String requestHeader,
        String requestPayload,
        String responseHeader,
        String responsePayload,
        @NotNull(message = "요청 시간이 없어요")
        LocalDateTime requestTime,
        @NotNull(message = "응답 시간이 없어요")
        LocalDateTime responseTime
) {

}
