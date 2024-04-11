package com.pp.api.service.command;

import jakarta.validation.*;

import java.util.Set;

public abstract class CommandSelfValidator<T> {

    private final Validator validator;

    public CommandSelfValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @SuppressWarnings(value = "unchecked")
    protected void validate() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}