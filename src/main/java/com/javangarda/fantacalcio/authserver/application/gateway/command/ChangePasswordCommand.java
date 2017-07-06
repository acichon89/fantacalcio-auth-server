package com.javangarda.fantacalcio.authserver.application.gateway.command;

import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.DifferentPassword;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.EqualFields;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.PasswordCheck;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.StrongPassword;
import lombok.Value;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Value(staticConstructor = "of")
@EqualFields(baseField = "password", matchField = "confirmPassword", message = "validation.passwordsnotequal")
public class ChangePasswordCommand {
    @Email
    @NotBlank
    private String email;
    @PasswordCheck(message = "validation.password.incorrect", query = "SELECT password FROM users WHERE email= ?")
    private String oldPassword;
    @StrongPassword
    @DifferentPassword
    private String password;
    private String confirmPassword;
}
