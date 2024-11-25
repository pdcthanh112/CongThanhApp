package com.congthanh.shippingservice.model.entity;

import com.congthanh.shippingservice.constant.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long order;

    private String customer;

    private String shippingAddress;

    private ShippingStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
