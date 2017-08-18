package com.javangarda.fantacalcio.authserver.application.gateway.command;

import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.DifferentPassword;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.EqualFields;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.PasswordCheck;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.StrongPassword;
import lombok.Value;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Value(staticConstructor = "of")
public class ChangePasswordCommand {
    private String email;
    private String oldPassword;
    private String password;
}
