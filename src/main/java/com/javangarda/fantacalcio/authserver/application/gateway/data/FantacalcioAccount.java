package com.javangarda.fantacalcio.authserver.application.gateway.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javangarda.fantacalcio.authserver.application.internal.storage.Account;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountStatus;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.stream.Collectors;

@JsonIgnoreProperties(value = {"password"})
@EqualsAndHashCode
public class FantacalcioAccount extends User {

    private String id;

    public FantacalcioAccount(Account account){
        super(account.getEmail(),account.getPassword(), account.hasStatus(AccountStatus.VALID),
                account.hasStatus(AccountStatus.VALID), account.hasStatus(AccountStatus.VALID), account.hasStatus(AccountStatus.VALID),
                account.allRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toCollection(HashSet::new)));
        this.id=account.getId();
    }
}
