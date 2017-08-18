package com.javangarda.fantacalcio.authserver.application.internal.storage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
public class Account {

    private static final String ROLE_DELIMETER = ",";

    @Getter @Setter
    private String id;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private AccountStatus accountStatus;
    @Getter @Setter
    private String roles;

    public Account() {}

    public Account(String id){
        this.id=id;
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
        return allRoles().contains(role);
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

    public Set<AccountRole> allRoles() {
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
        allRoles().stream().filter(roleAccount -> !roleAccount.name().equals(role)).forEach(roleAccount -> stringJoiner.add(roleAccount.name()));
        roles = stringJoiner.toString();
        return true;
    }

}
