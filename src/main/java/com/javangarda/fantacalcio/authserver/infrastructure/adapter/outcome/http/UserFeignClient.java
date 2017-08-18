package com.javangarda.fantacalcio.authserver.infrastructure.adapter.outcome.http;

import com.javangarda.fantacalcio.authserver.application.internal.UserVerificationDataProjection;
import com.javangarda.fantacalcio.authserver.infrastructure.config.FeignSecureConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fantacalcio-user")
public interface UserFeignClient {

    @GetMapping(value = "/usersWithEmailToConfirm")
    UserVerificationDataProjection getByTmpEmailAndVerificationToken( @RequestParam("email") String tmpEmail,
                                                                      @RequestParam("verificationToken") String verificationEmailToken);

    @GetMapping(value = "/usersWithForgottenPassword")
    UserVerificationDataProjection getByEmailAndResetPasswordToken( @RequestParam("email") String email,
                                                                      @RequestParam("resetPasswordToken") String token);
}
