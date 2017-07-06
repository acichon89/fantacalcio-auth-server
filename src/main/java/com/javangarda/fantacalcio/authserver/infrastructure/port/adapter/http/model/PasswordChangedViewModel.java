package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.http.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class PasswordChangedViewModel {
    private boolean success;
}
