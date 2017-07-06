package com.javangarda.fantacalcio.authserver.application.internal.storage;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends Repository<Account, String>{
    Account save(Account entity);
    @Query("UPDATE Account a SET a.password=:password WHERE email=:email")
    @Modifying
    void updatePassword(@Param("email") String email, @Param("password") String password);
}
