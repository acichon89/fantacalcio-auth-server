package com.javangarda.fantacalcio.authserver.infrastructure.adapter.income.validation;

import com.javangarda.fantacalcio.commons.authentication.CurrentUserResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class DifferentPasswordValidator implements ConstraintValidator<DifferentPassword, String> {

    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;
    private final CurrentUserResolver currentUserResolver;
    private final static String QUERY = "SELECT COUNT(*) FROM accounts WHERE email = ? AND password = ?";

    @Override
    public void initialize(DifferentPassword constraint) {

    }

    public DifferentPasswordValidator(PasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate, CurrentUserResolver currentUserResolver){
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
        this.currentUserResolver = currentUserResolver;
    }

    @Override
    public boolean isValid(String plainPassword, ConstraintValidatorContext context) {
        String email = currentUserResolver.resolveCurrentUserIdentity().get();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        int count = jdbcTemplate.queryForObject(QUERY, new Object[] {email, encodedPassword}, Integer.class);
        return count == 0;
    }



}