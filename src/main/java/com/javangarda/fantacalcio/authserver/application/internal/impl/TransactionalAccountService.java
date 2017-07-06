package com.javangarda.fantacalcio.authserver.application.internal.impl;

import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.internal.AccountFactory;
import com.javangarda.fantacalcio.authserver.application.internal.AccountService;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
public class TransactionalAccountService implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void storeAccount(CreateAccountCommand createAccountCommand) {
        accountRepository.save(accountFactory.create(createAccountCommand));
    }

    @Override
    public void changePassword(String email, String plainNewPassword) {
        String encodedPassword = passwordEncoder.encode(plainNewPassword);
        accountRepository.updatePassword(email, encodedPassword);
    }
}
