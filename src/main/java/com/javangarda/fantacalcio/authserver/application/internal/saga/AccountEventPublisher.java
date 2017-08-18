package com.javangarda.fantacalcio.authserver.application.internal.saga;


public interface AccountEventPublisher {
    void publishAccountCreated(AccountCreatedEvent event);
    void publishAccountPasswordChanged(AccountPasswordChangedEvent event);
    void publishAccountEmailChanged(AccountEmailChangedEvent event);
}
