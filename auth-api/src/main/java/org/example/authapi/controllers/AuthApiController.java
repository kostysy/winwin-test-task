package org.example.authapi.controllers;

import org.example.authapi.configuration.service.JwtService;
import org.example.authapi.dto.LoginRequest;
import org.example.authapi.entities.User;
import org.example.authapi.services.ProcessingService;
import org.example.authapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController("/api")
public class AuthApiController {
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ProcessingService processingService;

    public AuthApiController(JwtService jwtService, UserService userService, PasswordEncoder passwordEncoder, ProcessingService processingService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.processingService = processingService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        User user = userService.getUserByEmail(request.email());

        if (!user.getEmail().equals(request.email()) ||
                passwordEncoder.matches(request.email(), user.getEmail())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(request.email());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        if (userService.isUserExist(request.email())) {
            throw new RuntimeException("User already exists");
        } else if (request.email().isBlank() || request.password().isBlank()) {
            throw new RuntimeException("Email or password can't be blank");
        }

        userService.createUser(new User(request.email(), passwordEncoder.encode(request.password())));

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/process")
    public ResponseEntity<String> transform(@AuthenticationPrincipal String email, @RequestParam String text) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new RuntimeException("Invalid credentials");
        }

        return ResponseEntity.status(200)
                .body(processingService.process(text, email));
    }
}
