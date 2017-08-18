package com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.messaging;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Events {

    @Output
    MessageChannel accountCreatedChannel();

    @Output
    MessageChannel accountPasswordChangedChannel();

    @Output
    MessageChannel accountEmailChangedChannel();
}
