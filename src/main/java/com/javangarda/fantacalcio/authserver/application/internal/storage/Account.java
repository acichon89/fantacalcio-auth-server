package com.javangarda.fantacalcio.authserver.application.internal.storage;

import com.javangarda.fantacalcio.commons.entities.DefaultEntity;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "accounts")
public class Account extends DefaultEntity<String> {

    private static final String ROLE_DELIMETER = ",";

    @Getter
    private String email;
    @Getter
    private String password;
    @Getter
    private AccountStatus accountStatus;
    private String roles;

    public Account() {}

    public Account(String id){
        super(id);
    }

    public void assignValid(String email, String password){
        this.email=email;
        this.password=password;
        this.accountStatus=AccountStatus.VALID;
        grant(AccountRole.ROLE_BASIC);
    }

    public boolean hasStatus(AccountStatus status){
        return this.accountStatus.equals(status);
    }

    public boolean hasRole(AccountRole role){
        return getRoles().contains(role);
    }

    public boolean grant(AccountRole role){
        return addRole(role.name());
    }

    public boolean revoke(AccountRole role){
        return removeRole(role.name());
    }

    public void ban(){
        this.accountStatus = AccountStatus.BANNED;
    }

    public Set<AccountRole> getRoles() {
        return this.roles==null ? Collections.emptySet() : Arrays.stream(this.roles.split(ROLE_DELIMETER)).filter(AccountRole::isValid).map(AccountRole::valueOf).collect(Collectors.toCollection(HashSet::new));
    }

    private boolean addRole(String role){
        if(StringUtils.contains(roles, role)){
            return false;
        }
        StringJoiner stringJoiner = new StringJoiner(ROLE_DELIMETER);
        if(roles!=null){
            stringJoiner.add(roles);
        }
        stringJoiner.add(role);
        roles = stringJoiner.toString();
        return true;
    }

    private boolean removeRole(String role) {
        final StringJoiner stringJoiner = new StringJoiner(ROLE_DELIMETER);
        getRoles().stream().filter(roleAccount -> !roleAccount.name().equals(role)).forEach(roleAccount -> stringJoiner.add(roleAccount.name()));
        roles = stringJoiner.toString();
        return true;
    }

}
