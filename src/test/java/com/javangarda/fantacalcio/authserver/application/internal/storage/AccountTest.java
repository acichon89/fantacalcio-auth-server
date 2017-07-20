package com.javangarda.fantacalcio.authserver.application.internal.storage;

import org.junit.Test;

import static org.junit.Assert.*;


public class AccountTest {

    @Test
    public void should_has_status_valid() {
        //given:
        Account account = new Account("123");
        account.assignValid("john@doe.com", "pass123");
        //when:
        boolean isValid = account.hasStatus(AccountStatus.VALID);
        boolean isBasic = account.hasRole(AccountRole.ROLE_BASIC);
        boolean isPremium = account.hasRole(AccountRole.ROLE_PREMIUM);
        //then:
        assertTrue(isValid);
        assertTrue(isBasic);
        assertFalse(isPremium);
    }

    @Test
    public void should_assign_role() {
        //given:
        Account account = new Account("123");
        account.assignValid("john@doe.com", "pass123");
        account.grant(AccountRole.ROLE_MODERATOR);
        account.grant(AccountRole.ROLE_MODERATOR);
        //when:
        boolean isModerator = account.hasRole(AccountRole.ROLE_MODERATOR);
        int numberOfRoles = account.getRoles().size();
        //then:
        assertTrue(isModerator);
        assertEquals(2, numberOfRoles);
        account.printRoles();
    }

    @Test
    public void should_revoke_role() {
        //given:
        Account account = new Account("123");
        account.assignValid("john@doe.com", "pass123");
        account.grant(AccountRole.ROLE_MODERATOR);
        account.grant(AccountRole.ROLE_PREMIUM);
        //when:
        account.grant(AccountRole.ROLE_ADMIN);
        account.printRoles();
        account.revoke(AccountRole.ROLE_PREMIUM);
        int numberOfRoles = account.getRoles().size();
        //then:
        assertEquals(3, numberOfRoles);
    }
}