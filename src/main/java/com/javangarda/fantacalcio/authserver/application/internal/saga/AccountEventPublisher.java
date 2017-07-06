package com.javangarda.fantacalcio.authserver.application.internal.saga;


public interface AccountEventPublisher {
    void publishAccountCreated(AccountCreatedEvent event);
}
