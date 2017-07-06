package com.javangarda.fantacalcio.authserver.application.internal.saga;

public interface UserClient {
    boolean isVerified(String email, String verificationCode);
    boolean canResetThePassword(String email, String resetPasswordCode);
}
