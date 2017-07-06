package com.javangarda.fantacalcio.authserver.application.internal.saga;


import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangePasswordCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ResetPasswordCommand;

public interface CommandHandler {
    void handle(ChangePasswordCommand command);
    void handle(CreateAccountCommand command);
    void handle(ResetPasswordCommand command);
}
