package com.pp.api.controller.validator;

import com.pp.api.entity.enums.OauthUserClient;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.hasText;

public class AllowedOauthUserClientValidator implements ConstraintValidator<AllowedOauthUserClient, String> {

    @Override
    public void initialize(AllowedOauthUserClient constraintAnnotation) {
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        if (!hasText(value)) {
            return true;
        }

        return OauthUserClient.containsIgnoreCase(value);
    }

}
