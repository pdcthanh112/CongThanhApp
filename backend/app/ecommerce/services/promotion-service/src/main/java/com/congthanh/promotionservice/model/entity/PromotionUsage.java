package com.congthanh.promotionservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "promotion_usage")
public class PromotionUsage {

    @Id
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private String customer;

    @Column(name = "promotion_id", nullable = false)
    private Long promotion;

    @Column(name = "order_id", nullable = false)
    private Long order;

    @Column(name = "used_at", nullable = false)
    private Instant usedAt;
}
