package com.pp.api.configuration.oauth.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;

@JsonTypeInfo(use = CLASS)
@JsonAutoDetect(
        fieldVisibility = ANY,
        getterVisibility = NONE,
        isGetterVisibility = NONE
)
public abstract class ClientAuthenticationMethodMixIn {

    @JsonCreator
    ClientAuthenticationMethodMixIn(@JsonProperty(value = "value") String value) {
    }

}
