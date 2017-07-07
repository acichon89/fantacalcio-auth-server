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
        //then:
        assertTrue(isValid);
    }
}