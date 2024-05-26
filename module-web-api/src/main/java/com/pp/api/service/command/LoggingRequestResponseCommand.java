package com.pp.api.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class LoggingRequestResponseCommand extends CommandSelfValidator<LoggingRequestResponseCommand> {

    @NotBlank(message = "요청 traceId가 없습니다.")
    private final String traceId;

    @NotBlank(message = "요청 url이 없습니다.")
    private final String url;

    @NotNull(message = "요청 method가 없습니다.")
    private final HttpMethod method;

    @NotNull(message = "응답 status가 없습니다.")
    private final HttpStatus status;

    private final String requestHeader;

    private final String requestPayload;

    private final String responseHeader;

    private final String responsePayload;

    @NotNull(message = "요청 시간이 없습니다.")
    private final LocalDateTime requestTime;

    @NotNull(message = "응답 시간이 없습니다.")
    private final LocalDateTime responseTime;

    public LoggingRequestResponseCommand(
            String traceId,
            String url,
            HttpMethod method,
            HttpStatus status,
            String requestHeader,
            String requestPayload,
            String responseHeader,
            String responsePayload,
            LocalDateTime requestTime,
            LocalDateTime responseTime
    ) {
        this.traceId = traceId;
        this.url = url;
        this.method = method;
        this.status = status;
        this.requestHeader = requestHeader;
        this.requestPayload = requestPayload;
        this.responsePayload = responsePayload;
        this.responseHeader = responseHeader;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
        this.validate();
    }

}
