package com.javangarda.fantacalcio.authserver.application.gateway.command;

import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.EqualFields;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.StrongPassword;
import lombok.Value;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Value(staticConstructor = "of")
@EqualFields(baseField = "password", matchField = "confirmPassword", message = "validation.passwordsnotequal")
public class CreateAccountCommand {
    @Email
    @NotBlank
    private String email;
    @StrongPassword
    @NotBlank
    private String password;
    @NotBlank
    private String registrationToken;
}
