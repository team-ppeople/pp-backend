package com.pp.api.service;

import com.pp.api.entity.RequestResponseLogging;
import com.pp.api.repository.RequestResponseLoggingRepository;
import com.pp.api.service.command.LoggingRequestResponseCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestResponseLoggingService {

    private final RequestResponseLoggingRepository requestResponseLoggingRepository;

    public void logging(LoggingRequestResponseCommand command) {
        RequestResponseLogging requestResponseLogging = RequestResponseLogging.builder()
                .traceId(command.getTraceId())
                .url(command.getUrl())
                .method(command.getMethod())
                .status(command.getStatus())
                .requestHeader(command.getRequestHeader())
                .requestPayload(command.getRequestPayload())
                .responseHeader(command.getResponseHeader())
                .responsePayload(command.getResponsePayload())
                .requestTime(command.getRequestTime())
                .responseTime(command.getResponseTime())
                .build();

        requestResponseLoggingRepository.save(requestResponseLogging);
    }

    @Async(value = "persistenceLoggingExecutor")
    public void loggingAsync(LoggingRequestResponseCommand command) {
        logging(command);
    }

}
