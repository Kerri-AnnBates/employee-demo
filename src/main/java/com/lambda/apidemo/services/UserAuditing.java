package com.lambda.apidemo.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String uname;
        uname = "SYSTEM";
        return Optional.of(uname);
    }
}
