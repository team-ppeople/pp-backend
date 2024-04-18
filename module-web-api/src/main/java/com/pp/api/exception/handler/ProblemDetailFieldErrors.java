package com.pp.api.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ProblemDetailFieldErrors {

    private final String detailMessage;

    private final String field;

    private final Object rejectedValue;

    private final boolean bindingFailure;

    private final String code;

    public static ProblemDetailFieldErrors from(FieldError fieldError) {
        return new ProblemDetailFieldErrors(
                fieldError.getDefaultMessage(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.isBindingFailure(),
                fieldError.getCode()
        );
    }

}
