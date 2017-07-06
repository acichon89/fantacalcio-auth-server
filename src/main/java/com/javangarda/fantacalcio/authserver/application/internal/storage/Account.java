package com.javangarda.fantacalcio.authserver.application.internal.storage;

import com.javangarda.fantacalcio.commons.entities.DefaultEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends DefaultEntity<String> {
    private String email;
    private String password;
    private AccountStatus accountStatus;

    public Account() {}

    public Account(String id){
        super(id);
    }

    public void assignValid(String email, String password){
        this.email=email;
        this.password=password;
        this.accountStatus=AccountStatus.VALID;
    }

    public boolean hasStatus(AccountStatus status){
        return this.accountStatus.equals(status);
    }
}
