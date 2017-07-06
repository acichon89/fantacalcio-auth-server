package com.javangarda.fantacalcio.authserver.application.gateway.command;

import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.*;
import lombok.Value;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Value(staticConstructor = "of")
@EqualFields(baseField = "password", matchField = "confirmPassword", message = "validation.passwordsnotequal")
@UserVerified(emailField = "email", resetPasswordCodeField = "resetPasswordToken", message = "validation.usernotverified")
public class ResetPasswordCommand {
    @Email
    @NotBlank
    private String email;
    private String resetPasswordToken;
    @StrongPassword
    @DifferentPassword
    private String password;
    private String confirmPassword;
}
