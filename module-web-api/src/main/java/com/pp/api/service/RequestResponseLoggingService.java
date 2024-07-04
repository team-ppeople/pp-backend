package com.pp.api.service;

import com.pp.api.entity.RequestResponseLogging;
import com.pp.api.repository.RequestResponseLoggingRepository;
import com.pp.api.service.command.LoggingRequestResponseCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class RequestResponseLoggingService {

    private final RequestResponseLoggingRepository requestResponseLoggingRepository;

    public void logging(@Valid LoggingRequestResponseCommand command) {
        RequestResponseLogging requestResponseLogging = RequestResponseLogging.builder()
                .traceId(command.traceId())
                .url(command.url())
                .method(command.method())
                .status(command.status())
                .requestHeader(command.requestHeader())
                .requestPayload(command.requestPayload())
                .responseHeader(command.responseHeader())
                .responsePayload(command.responsePayload())
                .requestTime(command.requestTime())
                .responseTime(command.responseTime())
                .build();

        requestResponseLoggingRepository.save(requestResponseLogging);
    }

    @Async(value = "persistenceLoggingExecutor")
    public void loggingAsync(@Valid LoggingRequestResponseCommand command) {
        logging(command);
    }

}
