package com.pp.api.client.slack.dto;

public record SlackSendErrorMessageRequest(
        String requestURI,
        String traceId,
        Exception exception
) {

    public String buildMessage() {
        return "```" +
                "requestURI: " +
                requestURI +
                "\n" +
                "traceId: " +
                traceId +
                "\n" +
                "exception: " +
                exception.getClass().getName() +
                "\n" +
                "message: " +
                exception.getMessage() +
                "```";
    }

}
