package org.example.dataapi.services.impl;

import org.example.dataapi.services.HeaderValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HeaderValidationServiceImpl implements HeaderValidationService {
    private final String SECRET_TOKEN = "secret.token";
    private final Environment environment;

    @Autowired
    public HeaderValidationServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean validate(String token) {
        return Objects.equals(environment.getProperty(SECRET_TOKEN), token);
    }
}
