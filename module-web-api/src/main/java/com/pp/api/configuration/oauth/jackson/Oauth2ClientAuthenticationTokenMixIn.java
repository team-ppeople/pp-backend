package com.pp.api.configuration.oauth.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;

@JsonTypeInfo(use = CLASS)
@JsonAutoDetect(
        fieldVisibility = ANY,
        getterVisibility = NONE,
        isGetterVisibility = NONE
)
public abstract class Oauth2ClientAuthenticationTokenMixIn {

    @JsonCreator
    Oauth2ClientAuthenticationTokenMixIn(
            @JsonProperty(value = "clientId") String clientId,
            @JsonProperty(value = "clientAuthenticationMethod") ClientAuthenticationMethod clientAuthenticationMethod,
            @JsonProperty(value = "credentials") Object credentials,
            @JsonProperty(value = "additionalParameters") Map<String, Object> additionalParameters
    ) {
    }

}
