package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.http.model;


import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation.*;
import com.javangarda.fantacalcio.commons.validation.CurrentUser;
import com.javangarda.fantacalcio.commons.validation.RepositoryFieldExists;
import com.javangarda.fantacalcio.commons.validation.RepositoryFieldUnique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@UserWithTmpEmailVerified(emailField = "oldEmail", tokenField = "changeEmailToken", message = "validation.usernotverified")
public class ChangeEmailRequestModel {
    @Email
    @NotBlank
    @RepositoryFieldExists(query = "SELECT COUNT(id) FROM accounts WHERE email = ?", message = "validation.account.notfound")
    public String oldEmail;
    @Email
    @NotBlank
    @RepositoryFieldUnique(query = "SELECT COUNT(id FROM accounts WHERE email = ?", message = "validation.account.alreadyexists")
    public String newEmail;
    @NotBlank
    public String changeEmailToken;
}
