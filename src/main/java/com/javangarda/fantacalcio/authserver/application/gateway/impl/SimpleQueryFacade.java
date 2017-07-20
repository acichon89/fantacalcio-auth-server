package com.javangarda.fantacalcio.authserver.application.gateway.impl;


import com.javangarda.fantacalcio.authserver.application.gateway.QueryFacade;
import com.javangarda.fantacalcio.authserver.application.gateway.data.FantacalcioAccount;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class SimpleQueryFacade implements QueryFacade {

    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username).map(FantacalcioAccount::new).orElseThrow(()-> new UsernameNotFoundException("Account with email "+username+" not found."));
    }
}
