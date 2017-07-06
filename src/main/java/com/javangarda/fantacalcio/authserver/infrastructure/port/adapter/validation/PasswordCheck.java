package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordCheckValidator.class)
@Documented
public @interface PasswordCheck {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String query();
}
