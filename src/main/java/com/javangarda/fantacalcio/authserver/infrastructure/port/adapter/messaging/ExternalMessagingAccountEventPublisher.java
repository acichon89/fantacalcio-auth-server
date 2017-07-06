package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.messaging;


import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountCreatedEvent;
import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountEventPublisher;
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
}
