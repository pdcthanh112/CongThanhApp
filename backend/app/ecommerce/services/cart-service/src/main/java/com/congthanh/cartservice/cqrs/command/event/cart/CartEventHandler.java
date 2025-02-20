package com.congthanh.cartservice.cqrs.command.event.cart;

import com.congthanh.cartservice.config.RabbitMQConfig;
import com.congthanh.cartservice.constant.common.RabbitMQConstants;
import com.congthanh.cartservice.cqrs.command.event.cartItem.AddItemToCartCreatedEvent;
import com.congthanh.cartservice.model.entity.Cart;
import com.congthanh.cartservice.model.entity.CartItem;
import com.congthanh.cartservice.rabbitmq.cart.CartEventType;
import com.congthanh.cartservice.rabbitmq.cart.CartQueueEvent;
import com.congthanh.cartservice.repository.cart.CartRepository;
import com.congthanh.cartservice.repository.cartItem.CartItemRepository;
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

    private final CartItemRepository cartItemRepository;

    private final RabbitTemplate rabbitTemplate;

    @EventHandler
    public void on(CartCreatedEvent event) {
        Cart cart = Cart.builder()
                .id(event.getId())
                .name(event.getName())
                .customerId(event.getCustomerId())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .status(event.getStatus())
                .isDefault(event.isDefault())
                .build();
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP"+cart);
        cartRepository.save(cart);
        if (event.isDefault()) {
            cartRepository.setDefaultCartForCustomer(event.getCustomerId(), event.getId());
        }

        CartQueueEvent<CartCreatedEvent> queueEvent = CartQueueEvent.<CartCreatedEvent>builder()
                .eventType(CartEventType.CREATE_CART)
                .data(event)
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Cart.ROUTING_KEY, queueEvent);
    }

    @EventHandler
    public void on(AddItemToCartCreatedEvent event) {
        CartItem cartItem = CartItem.builder()
                .id(event.getId())
                .productId(event.getProductId())
                .quantity(event.getQuantity())
                .productVariant(event.getProductVariantId())
                .createdAt(event.getCreatedAt())
                .cartId(Cart.builder().id(event.getCartId()).build())
                .build();
        cartItemRepository.save(cartItem);

        CartQueueEvent<AddItemToCartCreatedEvent> queueEvent = CartQueueEvent.<AddItemToCartCreatedEvent>builder()
                .eventType(CartEventType.ADD_ITEM_TO_CART)
                .data(event)
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Cart.ROUTING_KEY, queueEvent);
    }
}
