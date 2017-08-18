package com.javangarda.fantacalcio.authserver.application.gateway;


import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangeEmailCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangePasswordCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ResetPasswordCommand;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface CommandBus {
    @Gateway(requestChannel = "createAccountCommandChannel")
    void createAccount(CreateAccountCommand createAccountCommand);
    @Gateway(requestChannel = "changePasswordCommandChannel")
    void changePassword(ChangePasswordCommand changePasswordCommand);
    @Gateway(requestChannel = "resetPasswordCommandChannel")
    void resetPassword(ResetPasswordCommand resetPasswordCommand);
    @Gateway(requestChannel = "changeEmailCommandChannel")
    void changeEmail(ChangeEmailCommand changeEmailCommand);
}
