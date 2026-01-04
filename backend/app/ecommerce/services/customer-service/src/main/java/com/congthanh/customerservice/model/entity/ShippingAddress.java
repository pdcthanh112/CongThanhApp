package com.congthanh.customerservice.model.entity;

import com.congthanh.commonservice.model.BaseEntity;
import com.congthanh.customerservice.constant.enums.AddressLabel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "shipping_address")
public class ShippingAddress extends BaseEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String customer;

    @Column(name = "full_name")
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private AddressLabel label = AddressLabel.HOME;

    private String country;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "address_line_3")
    private String addressLine3;

    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "longitude", precision = 10, scale = 8)
    private BigDecimal longitude;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "is_default")
    @JsonProperty("isDefault")
    private boolean isDefault;

}
