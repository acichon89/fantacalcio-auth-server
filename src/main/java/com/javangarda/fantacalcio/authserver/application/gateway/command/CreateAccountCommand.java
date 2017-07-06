package com.javangarda.fantacalcio.authserver.application.gateway.command;

import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.EqualFields;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.StrongPassword;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation.UserVerified;
import lombok.Value;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Value(staticConstructor = "of")
@EqualFields(baseField = "password", matchField = "confirmPassword", message = "validation.passwordsnotequal")
@UserVerified(emailField = "email", verificationCodeField = "registrationToken", message = "validation.usernotverified")
public class CreateAccountCommand {
    @Email
    @NotBlank
    private String email;
    @StrongPassword
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String registrationToken;
}
