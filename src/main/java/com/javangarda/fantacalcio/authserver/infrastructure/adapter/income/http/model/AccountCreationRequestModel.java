package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.http.model;

import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.EqualFields;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.StrongPassword;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.UserWithTmpEmailVerified;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@UserWithTmpEmailVerified(emailField = "email", tokenField = "registrationToken", message = "validation.usernotverified")
@EqualFields(baseField = "password", matchField = "confirmPassword", message = "validation.passwordsnotequal")
public class AccountCreationRequestModel {
    @Email
    @NotBlank
    public String email;
    @StrongPassword
    @NotBlank
    public String password;
    @NotBlank
    public String confirmPassword;
    @NotBlank
    public String registrationToken;
}
