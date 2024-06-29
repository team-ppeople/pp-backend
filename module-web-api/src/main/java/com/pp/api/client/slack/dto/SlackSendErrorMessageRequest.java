package com.pp.api.client.slack.dto;

public record SlackSendErrorMessageRequest(
        String requestURI,
        String traceId,
        Throwable exception
) {

    public String buildMessage() {
        return "🚨 ERROR\n" +
                "```" +
                "requestURI: " +
                requestURI +
                "\n\n" +
                "traceId: " +
                traceId +
                "\n\n" +
                "exception: " +
                exception.getClass().getName() +
                "\n\n" +
                "message: " +
                exception.getMessage() +
                "```";
    }

}
