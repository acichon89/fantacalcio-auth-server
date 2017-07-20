package com.javangarda.fantacalcio.authserver.application.internal.saga.impl;

import com.javangarda.fantacalcio.authserver.application.gateway.command.ChangePasswordCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.gateway.command.ResetPasswordCommand;
import com.javangarda.fantacalcio.authserver.application.internal.AccountService;
import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountCreatedEvent;
import com.javangarda.fantacalcio.authserver.application.internal.saga.AccountEventPublisher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class EventDrivenCommandHandlerTest {

    private AccountService accountService;
    private AccountEventPublisher accountEventPublisher;

    private EventDrivenCommandHandler eventDrivenCommandHandler;

    @Before
    public void setUp(){
        accountService = Mockito.mock(AccountService.class);
        accountEventPublisher = Mockito.mock(AccountEventPublisher.class);

        eventDrivenCommandHandler = new EventDrivenCommandHandler(accountService, accountEventPublisher);
    }

    @Test
    public void should_handle_create_command_and_send_event() {
        //given:
        CreateAccountCommand createAccountCommand = CreateAccountCommand.of("john@doe.com", "pass123", "pass123", "token-123");
        //when:
        eventDrivenCommandHandler.handle(createAccountCommand);
        //then:
        Mockito.verify(accountService).storeAccount(createAccountCommand);
        ArgumentCaptor<AccountCreatedEvent> eventAC = ArgumentCaptor.forClass(AccountCreatedEvent.class);
        Mockito.verify(accountEventPublisher).publishAccountCreated(eventAC.capture());
        assertEquals(eventAC.getValue().getEmail(), "john@doe.com");
    }

    @Test
    public void should_delegate_to_service_while_handle_reset_password() {
        ResetPasswordCommand command = ResetPasswordCommand.of("john@doe.com", "pass-token", "new-pass", "new-pass");
        //when
        eventDrivenCommandHandler.handle(command);
        //then:
        ArgumentCaptor<String> passwordAC = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> emailAC = ArgumentCaptor.forClass(String.class);
        Mockito.verify(accountService).changePassword(emailAC.capture(), passwordAC.capture());
        assertEquals(passwordAC.getValue(), "new-pass");
        assertEquals(emailAC.getValue(), "john@doe.com");
    }

    @Test
    public void should_delegate_to_service_while_handle_change_password() {
        ChangePasswordCommand command = ChangePasswordCommand.of("john@doe.com", "pass-token", "new-pass", "new-pass");
        //when
        eventDrivenCommandHandler.handle(command);
        //then:
        ArgumentCaptor<String> passwordAC = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> emailAC = ArgumentCaptor.forClass(String.class);
        Mockito.verify(accountService).changePassword(emailAC.capture(), passwordAC.capture());
        assertEquals(passwordAC.getValue(), "new-pass");
        assertEquals(emailAC.getValue(), "john@doe.com");
    }
}