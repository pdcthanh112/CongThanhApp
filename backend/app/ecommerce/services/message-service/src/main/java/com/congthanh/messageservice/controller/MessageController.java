package com.congthanh.messageservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/messages")
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public void updateStock(String productId, int newStock) {
        messagingTemplate.convertAndSend("/topic/stock/" + productId, newStock);
    }

}
