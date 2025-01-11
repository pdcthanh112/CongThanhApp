package com.congthanh.customerservice.cqrs.command.event.shippingAddress;

import com.congthanh.customerservice.constant.common.ErrorCode;
import com.congthanh.customerservice.constant.common.RabbitMQConstants;
import com.congthanh.customerservice.exception.ecommerce.NotFoundException;
import com.congthanh.customerservice.model.document.AddressDetailDocument;
import com.congthanh.customerservice.model.document.ShippingAddressDocument;
import com.congthanh.customerservice.rabbitmq.shippingAddress.ShippingAddressEventType;
import com.congthanh.customerservice.rabbitmq.shippingAddress.ShippingAddressQueueEvent;
import com.congthanh.customerservice.repository.shippingAddress.ShippingAddressDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShippingAddressEventListener {

    private final ShippingAddressDocumentRepository shippingAddressDocumentRepository;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConstants.ShippingAddress.QUEUE_NAME)
    public void handleShippingAddressEvent(@Payload ShippingAddressQueueEvent<Object> event) {
        switch (event.getEventType()) {
            case ShippingAddressEventType.CREATE -> {
                ShippingAddressCreatedEvent createdEvent = objectMapper.convertValue(event.getData(), ShippingAddressCreatedEvent.class);
                handleShippingAddressCreated(createdEvent);
            }
            case ShippingAddressEventType.UPDATE -> {
                ShippingAddressUpdatedEvent updatedEvent = objectMapper.convertValue(event.getData(), ShippingAddressUpdatedEvent.class);
                handleShippingAddressUpdated(updatedEvent);
            }
            case ShippingAddressEventType.DELETE -> {
                ShippingAddressDeletedEvent deletedEvent = objectMapper.convertValue(event.getData(), ShippingAddressDeletedEvent.class);
                handleShippingAddressDeleted(deletedEvent);
            }
        }
    }

    private void handleShippingAddressCreated(ShippingAddressCreatedEvent event) {
        ShippingAddressDocument shippingAddress = shippingAddressDocumentRepository
                .findByCustomer(event.getCustomer())
                .orElseGet(() -> {
                    ShippingAddressDocument newAddress = new ShippingAddressDocument();
                    newAddress.setCustomer(event.getCustomer());
                    newAddress.setAddress(new ArrayList<>());
                    return newAddress;
                });
        AddressDetailDocument addressDetail = AddressDetailDocument.builder()
                .id(event.getId())
                .fullName(event.getFullName())
                .phone(event.getPhone())
                .label(event.getLabel())
                .country(event.getCountry())
                .addressLine1(event.getAddressLine1())
                .addressLine2(event.getAddressLine2())
                .addressLine3(event.getAddressLine3())
                .street(event.getStreet())
                .postalCode(event.getPostalCode())
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .isDefault(event.isDefault())
                .build();
        if (addressDetail.isDefault()) {
            shippingAddress.getAddress().forEach(addr -> addr.setDefault(false));
        } else if (shippingAddress.getAddress().isEmpty()) {
            addressDetail.setDefault(true);
        }
        shippingAddress.getAddress().add(addressDetail);

        var result = shippingAddressDocumentRepository.save(shippingAddress);
        log.info("Save Address Detail {} into Mongo successfully, ID: {}", result.getCustomer(), result.getId());
    }

    private void handleShippingAddressUpdated(ShippingAddressUpdatedEvent event) {
        ShippingAddressDocument shippingAddress = shippingAddressDocumentRepository
                .findByCustomer(event.getCustomer())
                .orElseThrow(() -> new NotFoundException(String.format(ErrorCode.SHIPPING_ADDRESS_NOT_FOUND, event.getCustomer())));

        AddressDetailDocument existingAddress = shippingAddress.getAddress().stream()
                .filter(addr -> addr.getId().equals(event.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Address detail not found"));

        int addressIndex = shippingAddress.getAddress().indexOf(existingAddress);

        AddressDetailDocument updatedAddress = AddressDetailDocument.builder()
                .id(event.getId())
                .fullName(event.getFullName())
                .phone(event.getPhone())
                .label(event.getLabel())
                .country(event.getCountry())
                .addressLine1(event.getAddressLine1())
                .addressLine2(event.getAddressLine2())
                .addressLine3(event.getAddressLine3())
                .street(event.getStreet())
                .postalCode(event.getPostalCode())
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .isDefault(event.isDefault())
                .build();

        if (updatedAddress.isDefault()) {
            shippingAddress.getAddress().forEach(addr -> {
                if (!addr.getId().equals(event.getId())) {
                    addr.setDefault(false);
                }
            });
        } else if (existingAddress.isDefault()) {
            Optional<AddressDetailDocument> firstOtherAddress = shippingAddress.getAddress().stream()
                    .filter(addr -> !addr.getId().equals(event.getId()))
                    .findFirst();

            firstOtherAddress.ifPresent(addr -> addr.setDefault(true));
        }

        shippingAddress.getAddress().set(addressIndex, updatedAddress);

        var result = shippingAddressDocumentRepository.save(shippingAddress);
        log.info("Update Category {} into Mongo successfully, ID: {}", result.getCustomer(), result.getId());
    }

    private void handleShippingAddressDeleted(ShippingAddressDeletedEvent event) {
        ShippingAddressDocument shippingAddress = shippingAddressDocumentRepository
                .findByCustomer(event.getCustomer())
                .orElseThrow(() -> new NotFoundException(String.format(ErrorCode.SHIPPING_ADDRESS_NOT_FOUND, event.getId())));

        AddressDetailDocument existingAddress = shippingAddress.getAddress().stream()
                .filter(addr -> addr.getId().equals(event.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Address detail not found"));

        int addressIndex = shippingAddress.getAddress().indexOf(existingAddress);

        AddressDetailDocument removedAddress = shippingAddress.getAddress().remove(addressIndex);

        if (removedAddress.isDefault() && !shippingAddress.getAddress().isEmpty()) {
            shippingAddress.getAddress().getFirst().setDefault(true);
        }

        shippingAddressDocumentRepository.save(shippingAddress);

    }

}
