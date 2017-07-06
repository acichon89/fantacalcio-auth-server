package com.javangarda.fantacalcio.authserver.application.internal.impl;

import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.internal.AccountFactory;
import com.javangarda.fantacalcio.authserver.application.internal.storage.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@AllArgsConstructor
public class SimpleAccountFactory implements AccountFactory {

    private PasswordEncoder passwordEncoder;

    @Override
    public Account create(CreateAccountCommand createAccountCommand) {
        Account account = new Account(generateId());
        account.assignValid(createAccountCommand.getEmail(), passwordEncoder.encode(createAccountCommand.getPassword()));
        return account;
    }

    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
