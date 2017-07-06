package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation;

import com.javangarda.fantacalcio.authserver.application.internal.saga.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Slf4j
public class UserVerifiedValidator implements ConstraintValidator<UserVerified, Object> {

    private String emailField;
    private String verificationCodeField;
    private String resetPasswordCodeField;

    private final UserClient userClient;

    @Override
    public void initialize(UserVerified constraint) {
        emailField = constraint.emailField();
        verificationCodeField = constraint.verificationCodeField();
        resetPasswordCodeField = constraint.resetPasswordCodeField();
    }

    public UserVerifiedValidator(UserClient userClient){
        this.userClient = userClient;
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            String emailFieldValue = getFieldValue(object, emailField);
            String verificationCodeValue = getFieldValue(object, verificationCodeField);
            String resetPasswordCodeValue = getFieldValue(object, resetPasswordCodeField);
            return StringUtils.isNotBlank(verificationCodeValue) ? userClient.isVerified(emailFieldValue, verificationCodeValue) : userClient.canResetThePassword(emailFieldValue, resetPasswordCodeValue);
        } catch (Exception e) {
            log.error("Error while calling user client while checking user verification", e);
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