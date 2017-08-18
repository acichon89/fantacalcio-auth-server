package com.javangarda.fantacalcio.authserver.application.internal;


import java.util.Optional;

public interface UserClient {
    Optional<UserVerificationDataProjection> getUserWithEmailToVerify(String emailToConfirm, String verificationToken);
    Optional<UserVerificationDataProjection> getUserWithForgotPassword(String email, String resetPasswordToken);
}
