package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation;

import com.javangarda.fantacalcio.authserver.application.internal.UserClient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class UserWithTmpEmailVerifiedValidator implements ConstraintValidator<UserWithForgotPasswordVerified, Object> {

    private String emailField;
    private String confirmationTokenField;

    private final UserClient userClient;

    public UserWithTmpEmailVerifiedValidator(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void initialize(UserWithForgotPasswordVerified constraint) {
        emailField = constraint.emailField();
        confirmationTokenField = constraint.tokenField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            String email = getFieldValue(object, emailField);
            String token = getFieldValue(object, confirmationTokenField);
            return userClient.getUserWithForgotPassword(email, token).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    private String getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object).toString();
    }

}