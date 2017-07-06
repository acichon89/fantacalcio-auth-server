package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.messaging;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Events {

    @Output
    MessageChannel accountCreatedChannel();
}
