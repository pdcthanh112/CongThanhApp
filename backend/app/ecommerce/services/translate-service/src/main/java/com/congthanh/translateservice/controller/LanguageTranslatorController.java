package com.congthanh.translateservice.controller;

import com.congthanh.translateservice.model.LanguageTranslatorRequest;
import com.congthanh.translateservice.service.LanguageTranslatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LanguageTranslatorController {

    private final LanguageTranslatorService translatorService;

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody LanguageTranslatorRequest request) {
        String translation = translatorService.translate(request);
        return ResponseEntity.ok(translation);
    }

}
