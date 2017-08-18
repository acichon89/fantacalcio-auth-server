package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation;

import com.javangarda.fantacalcio.authserver.application.internal.UserClient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class UserWithForgotPasswordVerifiedValidator implements ConstraintValidator<UserWithTmpEmailVerified, Object> {

    private String emailField;
    private String confirmationTokenField;

    private final UserClient userClient;

    public UserWithForgotPasswordVerifiedValidator(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void initialize(UserWithTmpEmailVerified constraint) {
        emailField = constraint.emailField();
        confirmationTokenField = constraint.tokenField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            String email = getFieldValue(object, emailField);
            String token = getFieldValue(object, confirmationTokenField);
            return userClient.getUserWithEmailToVerify(email, token).isPresent();
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