package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.http.internal;


import com.javangarda.fantacalcio.authserver.application.internal.saga.UserClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

@AllArgsConstructor
@Slf4j
public class HttpUserClient implements UserClient {

    private final RestTemplate restTemplate;
    private static final String ENCODING = "UTF-8";
    private static final String USER_APP_NAME = "fantacalcio-user";

    @HystrixCommand(fallbackMethod = "negativeResponse")
    @Override
    public boolean isVerified(String email, String verificationCode) {
        try {
            UserDTO userDTO = this.restTemplate.getForObject("http://"+USER_APP_NAME+"/unconfirmedUser?token="+verificationCode+"&email="+ encode(email), UserDTO.class);
            return userDTO.getConfirmationToken().equals(verificationCode) && userDTO.getUnConfirmedEmail().equals(email);
        } catch (Exception e){
            log.error("Error while preparing or executing http call to "+USER_APP_NAME, e);
        } return false;
    }

    @HystrixCommand(fallbackMethod = "negativeResponse")
    @Override
    public boolean canResetThePassword(String email, String resetPasswordCode) {
        try {
            UserDTO userDTO = this.restTemplate.getForObject("http://"+USER_APP_NAME+"/userWithForgottenPassword?token="+resetPasswordCode+"&email="+ encode(email), UserDTO.class);
            return userDTO.getResetPasswordToken().equals(resetPasswordCode) && userDTO.getConfirmedEmail().equals(email);
        } catch (Exception e){
            log.error("Error while preparing or executing http call to "+USER_APP_NAME, e);
        } return false;
    }

    public boolean negativeResponse(){
        return false;
    }

    private String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, ENCODING);
    }
}
@Data
class UserDTO {
    private String id;
    private String fullName;
    private String confirmedEmail;
    private String unConfirmedEmail;
    private String confirmationToken;
    private String resetPasswordToken;
    private Locale emailLocale;
}