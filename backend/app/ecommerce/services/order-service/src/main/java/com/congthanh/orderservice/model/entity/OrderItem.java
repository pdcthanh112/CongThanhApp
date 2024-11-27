package com.congthanh.orderservice.model.entity;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "order_item")
public class OrderItem {

    @Id
    private Long id;

    private String productVariant;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders", nullable = false)
    private Order orders;

    private OrderStatus status;

}
