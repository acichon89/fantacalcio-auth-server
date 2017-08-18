package com.javangarda.fantacalcio.authserver.application.internal.saga;

import lombok.Value;

@Value(staticConstructor = "of")
public class AccountPasswordChangedEvent {
    private String email;
    private boolean withReset;
}
