package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.http;

import com.javangarda.fantacalcio.authserver.application.gateway.CommandBus;
import com.javangarda.fantacalcio.authserver.application.gateway.QueryFacade;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangeEmailCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangePasswordCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ResetPasswordCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.data.UserDTO;
import com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.http.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class AccountEndpoint {

    @Autowired
    private CommandBus commandBus;
    @Autowired
    private QueryFacade queryFacade;

    @GetMapping(value = "/userWithEmailToVerify")
    public ResponseEntity<UserDTO> userDTO(@RequestParam String email, @RequestParam String token){
        Optional<UserDTO> potentialUser = queryFacade.getChangeEmailTokenAndTmpEmail(token, email);
        return potentialUser.isPresent() ? ResponseEntity.ok(potentialUser.get()) : (ResponseEntity<UserDTO>) ResponseEntity.notFound();
    }

    //@PreAuthorize("#oauth2.hasScope('gui')")
    @PostMapping(value = "/createAccount")
    public ResponseEntity<SuccessActionViewModel> createAccount(@RequestBody @Valid AccountCreationRequestModel model){
        CreateAccountCommand command = CreateAccountCommand.of(model.email, model.password, model.registrationToken);
        commandBus.createAccount(command);
        return ResponseEntity.ok(SuccessActionViewModel.of(true, LocalDateTime.now()));
    }

    //@PreAuthorize("#oauth2.hasScope('gui')")
    @PatchMapping(value = "/changePassword")
    public ResponseEntity<SuccessActionViewModel> changePassword(@RequestBody @Valid ChangePasswordRequestModel model){
        commandBus.changePassword(ChangePasswordCommand.of(model.email, model.oldPassword, model.password));
        return ResponseEntity.ok(SuccessActionViewModel.of(true, LocalDateTime.now()));
    }

    //@PreAuthorize("#oauth2.hasScope('gui')")
    @PostMapping(value = "/resetPassword")
    public ResponseEntity<SuccessActionViewModel> resetPassword(@RequestBody @Valid ResetPasswordRequestModel model){
        commandBus.resetPassword(ResetPasswordCommand.of(model.email, model.resetPasswordToken, model.password));
        return ResponseEntity.ok(SuccessActionViewModel.of(true, LocalDateTime.now()));
    }

    //@PreAuthorize("#oauth2.hasScope('gui')")
    @PostMapping(value = "/changeEmail")
    public ResponseEntity<SuccessActionViewModel> changeEmail(@RequestBody @Validated ChangeEmailRequestModel model) {
        commandBus.changeEmail(ChangeEmailCommand.of(model.oldEmail, model.newEmail, model.changeEmailToken));
        return ResponseEntity.ok(SuccessActionViewModel.of(true, LocalDateTime.now()));
    }

    @PreAuthorize("#oauth2.hasScope('gui', 'server')")
    @GetMapping(value = "/current")
    public Principal currentUser(Principal principal){
        return principal;
    }
}
