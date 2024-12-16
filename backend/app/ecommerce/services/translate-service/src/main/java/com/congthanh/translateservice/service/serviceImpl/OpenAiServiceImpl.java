package com.congthanh.translateservice.service.serviceImpl;

import com.congthanh.translateservice.openai.AiAssistant;
import com.congthanh.translateservice.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements OpenAiService {
    private final AiAssistant aiAssistant;

    @Override
    public String translate(String text, String language) {
        return aiAssistant.translate(text, language);
    }
}
