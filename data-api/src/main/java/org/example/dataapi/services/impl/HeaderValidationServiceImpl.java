package org.example.dataapi.services.impl;

import org.example.dataapi.services.HeaderValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HeaderValidationServiceImpl implements HeaderValidationService {
    @Value("${http.internal.token}")
    private String secretToken;

    @Override
    public boolean validate(String token) {
        return Objects.equals(secretToken, token);
    }
}
