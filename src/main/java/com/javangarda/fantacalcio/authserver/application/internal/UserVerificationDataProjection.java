package com.javangarda.fantacalcio.authserver.application.internal;

import lombok.Value;

import java.util.Optional;

@Value(staticConstructor = "of")
public class UserVerificationDataProjection {
    private Optional<String> email;
    private Optional<String> tmpEmail;
    private Optional<String> emailVerificationToken;
    private Optional<String> resetPasswordToken;
    private String fullName;
}