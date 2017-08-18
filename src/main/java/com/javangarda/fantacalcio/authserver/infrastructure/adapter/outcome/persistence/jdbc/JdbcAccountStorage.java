package com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.persistence.jdbc;

import com.javangarda.fantacalcio.authserver.application.internal.storage.Account;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountStatus;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountStorage;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JdbcAccountStorage implements AccountStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Account store(Account account) {
        String query ="INSERT INTO accounts (id, email, account_status, password, roles) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(query, account.getId(), account.getEmail(), account.getAccountStatus().name(), account.getPassword(), account.getRoles());
        return account;
    }

    @Override
    public void updatePassword(String email, String password) {
        String query = "UPDATE accounts SET password=? WHERE email=?";
        jdbcTemplate.update(query, password, email);
    }

    @Override
    public void updateEmail(String oldEmail, String newEmail) {
        String query = "UPDATE accounts SET email=? WHERE email=?";
        jdbcTemplate.update(query, newEmail, oldEmail);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        String query ="SELECT id, email, account_status, password, roles FROM accounts WHERE email=?";
        List<Account> accounts = jdbcTemplate.query(query, new Object[]{email}, (rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getString("id"));
            account.setEmail(rs.getString("email"));
            account.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));
            account.setPassword(rs.getString("password"));
            account.setRoles(rs.getString("roles"));
            return account;
        });
        return CollectionUtils.isEmpty(accounts) ? Optional.empty() : Optional.ofNullable(accounts.get(0));
    }
}
