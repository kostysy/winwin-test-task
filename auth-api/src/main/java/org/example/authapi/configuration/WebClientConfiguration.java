package org.example.authapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Value("${internal.token}")
    private String internalToken;

    @Bean
    public WebClient internalWebClient() {
        return WebClient.builder().defaultHeader("X-Internal-Token", internalToken)
                .build();
    }
}
