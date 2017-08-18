package com.javangarda.fantacalcio.authserver.application.internal;

import com.javangarda.fantacalcio.authserver.application.gateway.command.CreateAccountCommand;
import com.javangarda.fantacalcio.authserver.application.internal.storage.Account;

public interface AccountFactory {
    Account createValid(CreateAccountCommand createAccountCommand);
}
