package com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.http;


import com.javangarda.fantacalcio.authserver.application.internal.UserClient;
import com.javangarda.fantacalcio.authserver.application.internal.UserVerificationDataProjection;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class HttpUserClient implements UserClient {

    private final UserFeignClient userFeignClient;

    @Override
    public Optional<UserVerificationDataProjection> getUserWithEmailToVerify(String emailToConfirm, String verificationToken) {
        return Optional.ofNullable(userFeignClient.getByTmpEmailAndVerificationToken(emailToConfirm, verificationToken));
    }

    @Override
    public Optional<UserVerificationDataProjection> getUserWithForgotPassword(String email, String resetPasswordToken) {
        return Optional.ofNullable(userFeignClient.getByEmailAndResetPasswordToken(email, resetPasswordToken));
    }
}
