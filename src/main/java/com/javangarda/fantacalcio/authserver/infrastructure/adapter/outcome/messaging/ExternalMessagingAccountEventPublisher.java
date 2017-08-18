package com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.messaging;


import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountCreatedEvent;
import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountEmailChangedEvent;
import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountEventPublisher;
import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountPasswordChangedEvent;
import lombok.AllArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@AllArgsConstructor
public class ExternalMessagingAccountEventPublisher implements AccountEventPublisher {

    private final Events events;

    @Override
    public void publishAccountCreated(AccountCreatedEvent accountCreatedEvent) {
        Message<AccountCreatedEvent> message = MessageBuilder.withPayload(accountCreatedEvent).build();
        events.accountCreatedChannel().send(message);
    }

    @Override
    public void publishAccountPasswordChanged(AccountPasswordChangedEvent event) {
        Message<AccountPasswordChangedEvent> message = MessageBuilder.withPayload(event).build();
        events.accountPasswordChangedChannel().send(message);
    }

    @Override
    public void publishAccountEmailChanged(AccountEmailChangedEvent event) {
        Message<AccountEmailChangedEvent> message = MessageBuilder.withPayload(event).build();
        events.accountEmailChangedChannel().send(message);
    }
}
