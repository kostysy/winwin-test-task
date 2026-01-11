package org.example.authapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Configuration
public class WebClientConfiguration {
    @Value("${http.internal.token}")
    private String internalToken;

    private final Environment environment;

    public WebClientConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public WebClient internalWebClient() {
        return WebClient.builder()
                .baseUrl(Objects.requireNonNull(environment.getProperty("data.api.base-url")))
                .defaultHeader("X-Internal-Token", internalToken)
                .build();
    }
}
