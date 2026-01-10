package org.example.dataapi.controllers;

import org.example.dataapi.services.HeaderValidationService;
import org.example.dataapi.services.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class DataApiController {
    private final HeaderValidationService validationService;
    private final TransformService transformService;

    @Autowired
    public DataApiController(HeaderValidationService validationService,
                             TransformService transformService) {
        this.validationService = validationService;
        this.transformService = transformService;
    }

    @PostMapping("/transform")
    public ResponseEntity<String> transformString(@RequestHeader("X-Internal-Token") String token, @RequestParam String text) {
        if (!validationService.validate(token)) {
            return ResponseEntity.status(403)
                    .build();
        }
        String transformedText = transformService.transform(text);
            return ResponseEntity.status(200)
                .body(transformedText);
    }
}
