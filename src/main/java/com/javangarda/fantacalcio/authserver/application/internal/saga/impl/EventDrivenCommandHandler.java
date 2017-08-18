package com.javangarda.fantacalcio.authserver.application.internal.saga.impl;

import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangeEmailCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangePasswordCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ResetPasswordCommand;
import com.javangarda.fantacalcio.authserver.application.internal.AccountService;
import com.javangarda.fantacalcio.authserver.application.internal.saga.*;
import lombok.AllArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;

@AllArgsConstructor
public class EventDrivenCommandHandler implements CommandHandler {

    private final AccountService accountService;
    private final AccountEventPublisher accountEventPublisher;

    @Override
    @ServiceActivator(inputChannel = "createAccountCommandChannel")
    public void handle(CreateAccountCommand createAccountCommand) {
        accountService.storeAccount(createAccountCommand);
        accountEventPublisher.publishAccountCreated(AccountCreatedEvent.of(createAccountCommand.getEmail(), createAccountCommand.getRegistrationToken()));
    }

    @Override
    @ServiceActivator(inputChannel = "resetPasswordCommandChannel")
    public void handle(ResetPasswordCommand command) {
        accountService.changePassword(command.getEmail(), command.getPassword());
        accountEventPublisher.publishAccountPasswordChanged(AccountPasswordChangedEvent.of(command.getEmail(), true));
    }

    @Override
    public void handle(ChangeEmailCommand command) {
        accountService.changeEmail(command.getOldEmail(), command.getNewEmail());
        accountEventPublisher.publishAccountEmailChanged(AccountEmailChangedEvent.of(command.getOldEmail(), command.getNewEmail(), command.getConfirmationToken()));
    }

    @Override
    @ServiceActivator(inputChannel = "changePasswordCommandChannel")
    public void handle(ChangePasswordCommand changePasswordCommand) {
        accountService.changePassword(changePasswordCommand.getEmail(), changePasswordCommand.getPassword());
        accountEventPublisher.publishAccountPasswordChanged(AccountPasswordChangedEvent.of(changePasswordCommand.getEmail(), false));
    }
}
