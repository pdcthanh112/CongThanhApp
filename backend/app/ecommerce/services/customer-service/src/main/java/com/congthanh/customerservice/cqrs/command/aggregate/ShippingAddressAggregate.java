package com.congthanh.customerservice.cqrs.command.aggregate;

import com.congthanh.customerservice.constant.enums.AddressLabel;
import com.congthanh.customerservice.cqrs.command.command.shippingAddress.CreateShippingAddressCommand;
import com.congthanh.customerservice.cqrs.command.command.shippingAddress.DeleteShippingAddressCommand;
import com.congthanh.customerservice.cqrs.command.command.shippingAddress.UpdateShippingAddressCommand;
import com.congthanh.customerservice.cqrs.command.event.shippingAddress.ShippingAddressCreatedEvent;
import com.congthanh.customerservice.cqrs.command.event.shippingAddress.ShippingAddressDeletedEvent;
import com.congthanh.customerservice.cqrs.command.event.shippingAddress.ShippingAddressUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ShippingAddressAggregate {

    @AggregateIdentifier
    private Long id;
    private String customer;
    private String fullName;
    private String phone;
    private AddressLabel label;
    private String country;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String street;
    private String postalCode;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private boolean isDefault;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;

    public ShippingAddressAggregate() {
    }

    @CommandHandler
    public ShippingAddressAggregate(CreateShippingAddressCommand command) {
        apply(ShippingAddressCreatedEvent.builder()
                .id(command.getId())
                .customer(command.getCustomer())
                .fullName(command.getFullName())
                .phone(command.getPhone())
                .label(command.getLabel())
                .country(command.getCountry())
                .addressLine1(command.getAddressLine1())
                .addressLine2(command.getAddressLine2())
                .addressLine3(command.getAddressLine3())
                .street(command.getStreet())
                .postalCode(command.getPostalCode())
                .longitude(command.getLongitude())
                .latitude(command.getLatitude())
                .isDefault(command.isDefault())
                .createdAt(command.getCreatedAt())
                .createdBy(command.getCreatedBy())
                .updatedAt(command.getUpdatedAt())
                .updatedBy(command.getUpdatedBy())
                .build());
    }

    @EventSourcingHandler
    public void onShippingAddressCreated(ShippingAddressCreatedEvent event) {
        this.id = event.getId();
        this.customer = event.getCustomer();
        this.fullName = event.getFullName();
        this.phone = event.getPhone();
        this.label = event.getLabel();
        this.country = event.getCountry();
        this.addressLine1 = event.getAddressLine1();
        this.addressLine2 = event.getAddressLine2();
        this.addressLine3 = event.getAddressLine3();
        this.street = event.getStreet();
        this.postalCode = event.getPostalCode();
        this.longitude = event.getLongitude();
        this.latitude = event.getLatitude();
        this.isDefault = event.isDefault();
        this.createdAt = event.getCreatedAt();
        this.createdBy = event.getCreatedBy();
        this.updatedAt = event.getUpdatedAt();
        this.updatedBy = event.getUpdatedBy();
    }

    @CommandHandler
    public void handleUpdateShippingAddress(UpdateShippingAddressCommand command) {
        apply(ShippingAddressUpdatedEvent.builder()
                .id(command.getId())
                .customer(command.getCustomer())
                .fullName(command.getFullName())
                .phone(command.getPhone())
                .label(command.getLabel())
                .country(command.getCountry())
                .addressLine1(command.getAddressLine1())
                .addressLine2(command.getAddressLine2())
                .addressLine3(command.getAddressLine3())
                .street(command.getStreet())
                .postalCode(command.getPostalCode())
                .longitude(command.getLongitude())
                .latitude(command.getLatitude())
                .isDefault(command.isDefault())
                .createdAt(command.getCreatedAt())
                .createdBy(command.getCreatedBy())
                .updatedAt(command.getUpdatedAt())
                .updatedBy(command.getUpdatedBy())
                .build());
    }

    @EventSourcingHandler
    public void onShippingAddressUpdated(ShippingAddressUpdatedEvent event) {
        this.id = event.getId();
        this.customer = event.getCustomer();
        this.fullName = event.getFullName();
        this.phone = event.getPhone();
        this.label = event.getLabel();
        this.country = event.getCountry();
        this.addressLine1 = event.getAddressLine1();
        this.addressLine2 = event.getAddressLine2();
        this.addressLine3 = event.getAddressLine3();
        this.street = event.getStreet();
        this.postalCode = event.getPostalCode();
        this.longitude = event.getLongitude();
        this.latitude = event.getLatitude();
        this.isDefault = event.isDefault();
        this.createdAt = event.getCreatedAt();
        this.createdBy = event.getCreatedBy();
        this.updatedAt = event.getUpdatedAt();
        this.updatedBy = event.getUpdatedBy();
    }

    @CommandHandler
    public void handleDeleteShippingAddress(DeleteShippingAddressCommand command) {
        apply(ShippingAddressDeletedEvent.builder()
                .id(command.getId())
                .build());
    }

    @EventSourcingHandler
    public void onShippingAddressDeleted(ShippingAddressDeletedEvent event) {
        this.id = event.getId();
        this.customer = event.getCustomer();
        this.fullName = event.getFullName();
        this.phone = event.getPhone();
        this.label = event.getLabel();
        this.country = event.getCountry();
        this.addressLine1 = event.getAddressLine1();
        this.addressLine2 = event.getAddressLine2();
        this.addressLine3 = event.getAddressLine3();
        this.street = event.getStreet();
        this.postalCode = event.getPostalCode();
        this.longitude = event.getLongitude();
        this.latitude = event.getLatitude();
        this.isDefault = event.isDefault();
        this.createdAt = event.getCreatedAt();
        this.createdBy = event.getCreatedBy();
        this.updatedAt = event.getUpdatedAt();
        this.updatedBy = event.getUpdatedBy();
    }
}
