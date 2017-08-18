package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface StrongPassword {
    String message() default "validation.password.notstrong";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
