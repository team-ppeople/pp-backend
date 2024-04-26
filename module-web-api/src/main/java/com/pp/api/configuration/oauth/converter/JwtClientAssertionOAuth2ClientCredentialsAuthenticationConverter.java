package com.pp.api.configuration.oauth.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;
import static org.springframework.util.StringUtils.delimitedListToStringArray;
import static org.springframework.util.StringUtils.hasText;

public final class JwtClientAssertionOAuth2ClientCredentialsAuthenticationConverter implements AuthenticationConverter {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = extractFormParameters(request);

        if (!CLIENT_CREDENTIALS.getValue().equals(parameters.getFirst(GRANT_TYPE))) {
            return null;
        }

        if (!parameters.containsKey(CLIENT_ASSERTION)) {
            return null;
        }

        if (!parameters.containsKey(CLIENT_ASSERTION_TYPE)) {
            return null;
        }

        String scope = parameters.getFirst(SCOPE);

        if (hasText(scope) && parameters.get(SCOPE).size() != 1) {
            OAuth2Error error = new OAuth2Error(
                    INVALID_REQUEST,
                    "OAuth 2.0 Parameter: " + SCOPE,
                    ERROR_URI
            );

            throw new OAuth2AuthenticationException(error);
        }

        Set<String> requestedScopes = Set.of();

        if (hasText(scope)) {
            requestedScopes = new HashSet<>(asList(delimitedListToStringArray(scope, " ")));
        }

        Map<String, Object> additionalParameters = new HashMap<>();

        parameters.forEach((key, value) -> {
            if (!key.equals(GRANT_TYPE) && !key.equals(SCOPE)) {
                additionalParameters.put(
                        key,
                        value.size() == 1 ? value.get(0) : value.toArray(new String[0])
                );
            }
        });

        return new JwtClientAssertionOauth2ClientCredentialsAuthenticationToken(
                SecurityContextHolder.getContext()
                        .getAuthentication(),
                requestedScopes,
                additionalParameters
        );
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