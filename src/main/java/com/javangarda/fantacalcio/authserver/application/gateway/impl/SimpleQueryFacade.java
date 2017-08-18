package com.javangarda.fantacalcio.authserver.application.gateway.impl;


import com.javangarda.fantacalcio.authserver.application.gateway.QueryFacade;
import com.javangarda.fantacalcio.authserver.application.gateway.data.FantacalcioAccount;
import com.javangarda.fantacalcio.authserver.application.gateway.data.UserDTO;
import com.javangarda.fantacalcio.authserver.application.internal.UserClient;
import com.javangarda.fantacalcio.authserver.application.internal.UserVerificationDataProjection;
import com.javangarda.fantacalcio.authserver.application.internal.storage.AccountStorage;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@AllArgsConstructor
public class SimpleQueryFacade implements QueryFacade {

    private AccountStorage accountStorage;
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountStorage.findByEmail(username).map(FantacalcioAccount::new).orElseThrow(()-> new UsernameNotFoundException("Account with email "+username+" not found."));
    }

    @Override
    public Optional<UserDTO> getChangeEmailTokenAndTmpEmail(String token, String emailToConfirm) {
        return userClient.getUserWithEmailToVerify(emailToConfirm, token).map(this::mapUser);
    }

    private UserDTO mapUser(UserVerificationDataProjection userVerificationDataProjection){
        UserDTO dto = new UserDTO();
        dto.setEmail(userVerificationDataProjection.getTmpEmail().get());
        dto.setFullName(userVerificationDataProjection.getFullName());
        return dto;
    }
}
