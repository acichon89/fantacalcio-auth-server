package com.javangarda.fantacalcio.authserver.application.internal.saga;

import lombok.Value;

@Value(staticConstructor = "of")
public class AccountCreatedEvent {
    private String email;
}
