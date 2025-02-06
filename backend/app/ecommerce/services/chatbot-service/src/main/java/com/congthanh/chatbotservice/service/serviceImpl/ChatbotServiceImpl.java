package com.congthanh.chatbotservice.service.serviceImpl;

import com.congthanh.chatbotservice.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final ChatClient.Builder chatClient;

    @Override
    public Flux<String> generateResponse(String message) {
        ChatClient chatClientt = chatClient.build();

        Flux<String> response = chatClientt.prompt(message).stream().content();

        return response;
    }
}
