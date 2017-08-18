package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.http.model;

import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.*;
import com.javangarda.fantacalcio.commons.validation.CurrentUser;
import lombok.Value;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@UserWithForgotPasswordVerified(emailField = "email", tokenField = "resetPasswordToken", message = "validation.usernotverified")
@EqualFields(baseField = "password", matchField = "confirmPassword", message = "validation.passwordsnotequal")
public class ResetPasswordRequestModel {
    @Email
    @NotBlank
    public String email;
    public String resetPasswordToken;
    @StrongPassword
    @DifferentPassword
    public String password;
    public String confirmPassword;
}
