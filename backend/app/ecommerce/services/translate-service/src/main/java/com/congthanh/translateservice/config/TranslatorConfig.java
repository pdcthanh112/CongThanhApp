package com.congthanh.translateservice.config;

import com.congthanh.translateservice.openai.AiAssistant;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranslatorConfig {

    @Value("${openai.api-key}")
    String apiKey;

    @Bean
    public ChatLanguageModel model() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .timeout(ofSeconds(60))
                .build();
    }

    @Bean
    public AiAssistant aiAssistant(ChatLanguageModel model) {
        return AiServices.create(AiAssistant.class, model);
    }
}
