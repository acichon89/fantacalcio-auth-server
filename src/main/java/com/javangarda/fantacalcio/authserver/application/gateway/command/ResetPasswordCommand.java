package com.javangarda.fantacalcio.authserver.application.gateway.command;

import lombok.Value;

@Value(staticConstructor = "of")
public class ResetPasswordCommand {
    private String email;
    private String resetPasswordToken;
    private String password;
}
