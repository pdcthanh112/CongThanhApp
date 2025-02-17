package com.congthanh.cartservice.cqrs.command.event.cart;

import com.congthanh.cartservice.config.RabbitMQConfig;
import com.congthanh.cartservice.constant.common.RabbitMQConstants;
import com.congthanh.cartservice.model.entity.Cart;
import com.congthanh.cartservice.rabbitmq.cart.CartEventType;
import com.congthanh.cartservice.rabbitmq.cart.CartQueueEvent;
import com.congthanh.cartservice.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("cart")
@RequiredArgsConstructor
@Slf4j
public class CartEventHandler {

    private final CartRepository cartRepository;

    private final RabbitTemplate rabbitTemplate;

    @EventHandler
    public void on(CartCreatedEvent event) {
        Cart cart = Cart.builder()
                .id(event.getId())
                .name(event.getName())
                .customer(event.getCustomerId())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .status(event.getStatus())
                .isDefault(event.isDefault())
                .build();
        cartRepository.save(cart);
        if (event.isDefault()) {
            cartRepository.setDefaultCartForCustomer(event.getCustomerId(), event.getId());
        }

        CartQueueEvent<CartCreatedEvent> queueEvent = CartQueueEvent.<CartCreatedEvent>builder()
                .eventType(CartEventType.CREATE)
                .data(event)
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Cart.ROUTING_KEY, queueEvent);
    }
}
