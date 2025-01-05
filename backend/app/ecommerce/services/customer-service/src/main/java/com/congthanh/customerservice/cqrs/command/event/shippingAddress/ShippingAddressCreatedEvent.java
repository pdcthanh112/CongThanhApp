package com.congthanh.customerservice.cqrs.command.event.shippingAddress;

import com.congthanh.customerservice.constant.enums.AddressLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class ShippingAddressCreatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

}
