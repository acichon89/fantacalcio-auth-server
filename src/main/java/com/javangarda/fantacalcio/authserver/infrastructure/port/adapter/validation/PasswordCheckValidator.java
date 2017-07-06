package com.javangarda.fantacalcio.authserver.infrastructure.port.adapter.validation;


import com.javangarda.fantacalcio.commons.authentication.CurrentUserResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;


public class PasswordCheckValidator implements ConstraintValidator<PasswordCheck, String> {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserResolver currentUserResolver;

    private Optional<String> userEmail;
    private String query;

    public PasswordCheckValidator(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, CurrentUserResolver currentUserResolver) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.currentUserResolver = currentUserResolver;
    }

    @Override
    public void initialize(PasswordCheck constraintAnnotation) {
        this.userEmail = currentUserResolver.resolveCurrentUserIdentity();
        this.query=constraintAnnotation.query();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(userEmail.isPresent()){
            String currentUserEmail = userEmail.get();
            String encodedPassword = jdbcTemplate.queryForObject(query, new Object[] {currentUserEmail}, String.class);
            return passwordEncoder.matches(value, encodedPassword);
        } else {
            return false;
        }
    }


}
