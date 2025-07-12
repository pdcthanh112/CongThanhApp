package com.congthanh.promotionservice.model.entity;

import com.congthanh.commonservice.model.BaseEntity;
import com.congthanh.promotionservice.constant.enums.PromotionStatus;
import com.congthanh.promotionservice.constant.enums.PromotionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "promotions")
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @Column(nullable = false)
    private float value;

    @Column(name = "usage_limit")
    private int usageLimit;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private PromotionStatus status;
}
