package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {


    @Override
    public void initialize(StrongPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return containsNumber(value) && hasUpperCase(value) && hasLowerCase(value);
    }

    private boolean containsNumber(String value){
        return value.matches(".*\\d+.*");
    }

    private boolean hasUpperCase(String value){
        return !value.equals(value.toLowerCase());
    }

    private boolean hasLowerCase(String value){
        return !value.equals(value.toUpperCase());
    }
}

