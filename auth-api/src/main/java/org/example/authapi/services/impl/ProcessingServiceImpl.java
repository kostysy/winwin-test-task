package org.example.authapi.services.impl;

import org.example.authapi.entities.ProcessingLog;
import org.example.authapi.entities.User;
import org.example.authapi.services.ProcessingLogService;
import org.example.authapi.services.ProcessingService;
import org.example.authapi.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProcessingServiceImpl implements ProcessingService {
    private final WebClient webClient;
    private final ProcessingLogService logService;
    private final UserService userService;

    public ProcessingServiceImpl(@Qualifier("internalWebClient") WebClient webClient, @Qualifier("processingLogService") ProcessingLogService logService, UserService userService) {
        this.webClient = webClient;
        this.logService = logService;
        this.userService = userService;
    }

    @Override
    public String process(String text, String email) {
        if (!userService.isUserExist(email)) {
            throw new RuntimeException("User with this email:" + email + " was not found");
        }

        String transformedText = getDataApiTransform(text);

        User user = userService.getUserByEmail(email);
        logService.create(new ProcessingLog(user, text, transformedText));

        return transformedText;
    }

    private String getDataApiTransform(String text) {
        System.out.println("\n\n WEBCLIENT URL: " + webClient.mutate() + "\n\n");
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/transform")
                        .queryParam("text", text)
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Data API error: " + body))
                )
                .bodyToMono(String.class)
                .block();
    }
}
