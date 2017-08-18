package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.http.model;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class SuccessActionViewModel {
    private boolean success;
    private LocalDateTime responseTime;
}
