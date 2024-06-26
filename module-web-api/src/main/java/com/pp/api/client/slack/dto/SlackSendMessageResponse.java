package com.pp.api.client.slack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class SlackSendMessageResponse {

    private boolean ok;

}
