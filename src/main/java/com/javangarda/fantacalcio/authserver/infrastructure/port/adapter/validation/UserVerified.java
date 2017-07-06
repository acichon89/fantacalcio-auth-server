package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualFieldsValidator.class)
@Documented
public @interface UserVerified {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String emailField();
    String verificationCodeField() default "";
    String resetPasswordCodeField() default "";
}