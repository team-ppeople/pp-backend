package com.pp.api.controller.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.hasText;

public class EnumMatchValidator implements ConstraintValidator<EnumMatch, String> {

    private EnumMatch annotation;

    @Override
    public void initialize(EnumMatch annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        if (!hasText(value)) {
            return true;
        }

        Object[] enumValues = this.annotation.enumClass()
                .getEnumConstants();

        if (enumValues == null) {
            return false;
        }

        for (Object enumValue : enumValues) {
            if (value.equals(enumValue.toString())) {
                return true;
            }

            if (annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString())) {
                return true;
            }
        }

        return false;
    }

}
