package com.javangarda.fantacalcio.authserver.application.gateway.command;

import lombok.Value;

@Value(staticConstructor = "of")
public class ChangeEmailCommand {
    private String oldEmail;
    private String newEmail;
    private String confirmationToken;
}
