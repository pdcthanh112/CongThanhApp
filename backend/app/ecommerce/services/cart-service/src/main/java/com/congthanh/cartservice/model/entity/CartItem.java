package com.congthanh.cartservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items")
public class CartItem {

    @Id
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_variant_id", nullable = false)
    private String productVariant;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart", nullable = false)
    private Cart cart;

    @Column(name = "created_at")
    private long createdAt;

}
