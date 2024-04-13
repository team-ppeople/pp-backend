package com.pp.api.controller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = {FIELD, PARAMETER})
@Constraint(validatedBy = AllowedOauthUserClientValidator.class)
public @interface AllowedOauthUserClient {

    String message() default "허용되지 않은 Oauth 유저 클라이언트입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
