package com.pp.api.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Table(
        indexes = @Index(
                name = "request_response_logging_status_index",
                columnList = "status"
        )
)
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class RequestResponseLogging {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36)
    private String traceId;

    @Column(nullable = false, length = 2083)
    private String url;

    @Column(nullable = false, length = 10)
    private String method;

    @Column(nullable = false, length = 3)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String requestPayload;

    @Column(columnDefinition = "TEXT")
    private String responsePayload;

    @Column(columnDefinition = "TEXT")
    private String requestHeader;

    @Column(columnDefinition = "TEXT")
    private String responseHeader;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    @Column(nullable = false)
    private LocalDateTime responseTime;

    @Builder
    private RequestResponseLogging(
            String traceId,
            String url,
            HttpMethod method,
            HttpStatus status,
            String requestPayload,
            String responsePayload,
            String requestHeader,
            String responseHeader,
            LocalDateTime requestTime,
            LocalDateTime responseTime
    ) {
        this.traceId = traceId;
        this.url = url;
        this.method = method.name();
        this.status = String.valueOf(status.value());
        this.requestPayload = requestPayload;
        this.responsePayload = responsePayload;
        this.requestHeader = requestHeader;
        this.responseHeader = responseHeader;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
    }

}
