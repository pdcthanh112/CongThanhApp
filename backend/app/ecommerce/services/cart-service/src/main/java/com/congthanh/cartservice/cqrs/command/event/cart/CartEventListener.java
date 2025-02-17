package com.congthanh.cartservice.cqrs.command.event.cart;

import com.congthanh.cartservice.constant.common.RabbitMQConstants;
import com.congthanh.cartservice.model.document.CartDocument;
import com.congthanh.cartservice.rabbitmq.cart.CartEventType;
import com.congthanh.cartservice.rabbitmq.cart.CartQueueEvent;
import com.congthanh.cartservice.repository.cart.CartDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartEventListener {

    private final CartDocumentRepository cartDocumentRepository;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConstants.Cart.QUEUE_NAME)
    public void handleCartEvent(CartQueueEvent event) {
        switch (event.getEventType()) {
            case CartEventType.CREATE -> {
                CartCreatedEvent createdEvent = objectMapper.convertValue(event.getData(), CartCreatedEvent.class);
                handleCartCreated(createdEvent);
            }
            case CartEventType.UPDATE -> {}
            case CartEventType.DELETE -> {}
        }
    }

    private void handleCartCreated(CartCreatedEvent event) {
        CartDocument cart = CartDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .customer(event.getCustomerId())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .status(event.getStatus())
                .isDefault(event.isDefault())
                .build();
        cartDocumentRepository.save(cart);
        if(event.isDefault()) {
            cartDocumentRepository.setDefaultCartForCustomer(event.getCustomerId(), cart.getId());
        }
    }
}
