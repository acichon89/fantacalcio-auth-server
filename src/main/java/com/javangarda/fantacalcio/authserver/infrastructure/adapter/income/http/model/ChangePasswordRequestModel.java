package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.http.model;


import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.DifferentPassword;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.EqualFields;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.PasswordCheck;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.StrongPassword;
import com.javangarda.fantacalcio.commons.validation.CurrentUser;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@EqualFields(baseField = "password", matchField = "confirmPassword", message = "validation.passwordsnotequal")
public class ChangePasswordRequestModel {
    @Email
    @NotBlank
    @CurrentUser
    public String email;
    @PasswordCheck(message = "validation.password.incorrect", query = "SELECT password FROM users WHERE email= ?")
    public String oldPassword;
    @StrongPassword
    @DifferentPassword
    public String password;
    public String confirmPassword;
}
