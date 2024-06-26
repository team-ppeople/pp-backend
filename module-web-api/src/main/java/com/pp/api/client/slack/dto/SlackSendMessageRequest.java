package com.pp.api.client.slack.dto;

public record SlackSendMessageRequest(
        String channel,
        String text
) {
}
