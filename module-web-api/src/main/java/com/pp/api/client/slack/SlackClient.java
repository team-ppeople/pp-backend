package com.pp.api.client.slack;

import com.pp.api.client.slack.dto.SlackSendErrorMessageRequest;
import com.pp.api.client.slack.dto.SlackSendMessageRequest;
import com.pp.api.client.slack.dto.SlackSendMessageResponse;
import com.pp.api.configuration.webclient.property.SlackClientProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackClient {

    @Qualifier(value = "slackWebClient")
    private final WebClient webClient;

    private final SlackClientProperty slackClientProperty;

    public void sendMessage(String message) {
        if (!slackClientProperty.enabled()) {
            return;
        }

        SlackSendMessageRequest request = new SlackSendMessageRequest(
                slackClientProperty.channelId(),
                message
        );

        webClient.post()
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SlackSendMessageResponse.class)
                .flatMap(response -> {
                    if (!response.isOk()) {
                        log.error(
                                "슬랙 에러 알림 발송에 실패했어요. message: {}",
                                message
                        );
                    }

                    return just(response);
                })
                .subscribe();
    }

    public void sendErrorMessage(SlackSendErrorMessageRequest request) {
        sendMessage(request.buildMessage());
    }

}
