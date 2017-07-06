package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.http.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class AccountCreatedViewModel {
    private boolean success;
    private String email;
}
