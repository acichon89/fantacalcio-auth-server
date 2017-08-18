package com.javangarda.fantacalcio.authserver.application.gateway;

import com.javangarda.fantacalcio.authserver.application.gateway.data.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface QueryFacade extends UserDetailsService {
    Optional<UserDTO> getChangeEmailTokenAndTmpEmail(String token, String emailToConfirm);
}
