package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserWithTmpEmailVerifiedValidator.class)
@Documented
public @interface UserWithForgotPasswordVerified {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String emailField();
    String tokenField();
}
