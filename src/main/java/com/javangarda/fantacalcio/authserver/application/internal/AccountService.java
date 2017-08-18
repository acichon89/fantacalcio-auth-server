package com.javangarda.fantacalcio.authserver.application.internal;


import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;

public interface AccountService {
    void storeAccount(CreateAccountCommand createAccountCommand);
    void changePassword(String email, String plainNewPassword);
    void changeEmail(String oldEmail, String newEmail);
}
