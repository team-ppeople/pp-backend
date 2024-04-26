package com.pp.api.client.apple;

import com.pp.api.client.apple.dto.AppleTokenResponse;
import com.pp.api.configuration.webclient.property.AppleClientProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;

@Component
@RequiredArgsConstructor
public class AppleClient {

    private final WebClient appliWebClient;

    private final AppleClientSecretGenerator appleClientSecretGenerator;

    private final AppleClientProperty appleClientProperty;

    public AppleTokenResponse token(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("client_id", appleClientProperty.clientId());
        formData.add("client_secret", appleClientSecretGenerator.getOrGenerate());
        formData.add("code", code);
        formData.add("grant_type", AUTHORIZATION_CODE.getValue());

        return appliWebClient.post()
                .uri(appleClientProperty.tokenEndpoint())
                .contentType(APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(AppleTokenResponse.class)
                .block();
    }

    public void revoke(
            String token,
            OAuth2TokenType tokenType
    ) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("client_id", appleClientProperty.clientId());
        formData.add("client_secret", appleClientSecretGenerator.getOrGenerate());
        formData.add("token", token);
        formData.add("token_type_hint", tokenType.getValue());

        appliWebClient.post()
                .uri(appleClientProperty.tokenEndpoint())
                .contentType(APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
