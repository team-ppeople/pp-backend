package com.pp.api.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.NONE;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.CLIENT_ID;
import static org.springframework.util.StringUtils.hasText;

public final class RevacationTokenEndpointPublicClientAuthenticationConverter implements AuthenticationConverter {

    private final String tokenRevocationEndpoint;

    public RevacationTokenEndpointPublicClientAuthenticationConverter(String tokenRevocationEndpoint) {
        this.tokenRevocationEndpoint = tokenRevocationEndpoint;
    }

    public Authentication convert(HttpServletRequest request) {
        if (!POST.matches(request.getMethod())) {
            return null;
        }

        if (!request.getRequestURI().equals(this.tokenRevocationEndpoint)) {
            return null;
        }

        MultiValueMap<String, String> parameters = extractFormParameters(request);

        String clientId = parameters.getFirst(CLIENT_ID);

        if (hasText(clientId) && parameters.get(CLIENT_ID).size() == 1) {
            parameters.remove(CLIENT_ID);

            Map<String, Object> additionalParameters = new HashMap<>();

            parameters.forEach((key, value) -> {
                additionalParameters.put(
                        key,
                        value.size() == 1 ? value.get(0) : value.toArray(new String[0])
                );
            });

            return new OAuth2ClientAuthenticationToken(
                    clientId,
                    NONE,
                    null,
                    additionalParameters
            );
        }

        throw new OAuth2AuthenticationException(INVALID_REQUEST);
    }

    private MultiValueMap<String, String> extractFormParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        parameterMap.forEach((key, values) -> {
            String queryString = hasText(request.getQueryString()) ? request.getQueryString() : "";

            if (!queryString.contains(key)) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }

        });

        return parameters;
    }

}
