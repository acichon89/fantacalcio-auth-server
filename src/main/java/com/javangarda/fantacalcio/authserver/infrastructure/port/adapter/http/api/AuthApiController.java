package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.http.api;

import com.javangarda.fantacalcio.authserver.application.gateway.CommandBus;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangePasswordCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.http.model.AccountCreatedViewModel;
import com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.http.model.PasswordChangedViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthApiController {

    @Autowired
    private CommandBus commandBus;

    @PostMapping(value = "/createAccount")
    public ResponseEntity<AccountCreatedViewModel> createAccount(@RequestBody @Valid CreateAccountCommand createAccountCommand){
        commandBus.createAccount(createAccountCommand);
        return ResponseEntity.ok(AccountCreatedViewModel.of(true, createAccountCommand.getEmail()));
    }

    @PatchMapping(value = "/changePassword")
    public ResponseEntity<PasswordChangedViewModel> changePassword(@RequestBody @Valid ChangePasswordCommand changePasswordCommand){
        commandBus.changePassword(changePasswordCommand);
        return ResponseEntity.ok(PasswordChangedViewModel.of(true));
    }
}
