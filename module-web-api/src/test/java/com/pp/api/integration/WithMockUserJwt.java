package com.pp.api.integration;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.security.test.context.support.TestExecutionEvent.TEST_METHOD;

@Target(value = {METHOD, TYPE})
@Retention(value = RUNTIME)
@Inherited
@WithSecurityContext(factory = WithMockUserJwtSecurityContextFactory.class)
public @interface WithMockUserJwt {

    @AliasFor("userId")
    long value() default 1L;

    @AliasFor("value")
    long userId() default 1L;

    String[] scopes() default {};

    @AliasFor(annotation = WithSecurityContext.class)
    TestExecutionEvent setupBefore() default TEST_METHOD;

}
