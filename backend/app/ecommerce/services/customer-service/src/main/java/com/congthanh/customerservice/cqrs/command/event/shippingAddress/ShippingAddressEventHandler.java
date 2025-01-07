package com.congthanh.customerservice.cqrs.command.event.shippingAddress;

import com.congthanh.customerservice.config.RabbitMQConfig;
import com.congthanh.customerservice.constant.common.RabbitMQConstants;
import com.congthanh.customerservice.model.entity.ShippingAddress;
import com.congthanh.customerservice.rabbitmq.shippingAddress.ShippingAddressEventType;
import com.congthanh.customerservice.rabbitmq.shippingAddress.ShippingAddressQueueEvent;
import com.congthanh.customerservice.repository.shippingAddress.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("shipping-address")
@RequiredArgsConstructor
@Slf4j
public class ShippingAddressEventHandler {

    private final ShippingAddressRepository shippingAddressRepository;

    private final RabbitTemplate rabbitTemplate;

    @EventHandler
    public void on(ShippingAddressCreatedEvent event) {
        ShippingAddress address = ShippingAddress.builder()
                .id(event.getId())
                .customer(event.getCustomer())
                .fullName(event.getFullName())
                .phone(event.getPhone())
                .label(event.getLabel())
                .country(event.getCountry())
                .addressLine1(event.getAddressLine1())
                .addressLine2(event.getAddressLine2())
                .addressLine3(event.getAddressLine3())
                .street(event.getStreet())
                .postalCode(event.getPostalCode())
                .longitude(event.getLongitude())
                .latitude(event.getLatitude())
                .isDefault(event.isDefault())
                .build();
        var result = shippingAddressRepository.save(address);
        log.info("Save Shipping Address {} into Postgres successfully, ID: {}", result.getCustomer(), result.getId());
        ShippingAddressQueueEvent<ShippingAddressCreatedEvent> queueEvent = ShippingAddressQueueEvent.<ShippingAddressCreatedEvent>builder()
                .eventType(ShippingAddressEventType.CREATE)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.ShippingAddress.ROUTING_KEY, queueEvent);
    }

}
