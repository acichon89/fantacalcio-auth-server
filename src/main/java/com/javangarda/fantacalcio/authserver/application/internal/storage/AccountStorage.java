package com.javangarda.fantacalcio.authserver.application.internal.storage;

import java.util.Optional;

public interface AccountStorage {
    Account store(Account entity);
    void updatePassword(String email, String password);
    void updateEmail(String oldEmail, String newEmail);
    Optional<Account> findByEmail(String email);
}
