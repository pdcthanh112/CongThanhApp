package com.congthanh.project.entity;

import com.congthanh.project.constant.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
