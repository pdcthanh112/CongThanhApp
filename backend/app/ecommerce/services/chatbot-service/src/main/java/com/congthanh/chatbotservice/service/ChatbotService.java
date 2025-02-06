package com.congthanh.chatbotservice.service;

import reactor.core.publisher.Flux;

public interface ChatbotService {

    Flux<String> generateResponse(String message);
}
