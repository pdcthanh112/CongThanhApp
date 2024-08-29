package com.congthanh.project.dto;

import com.congthanh.project.constant.enums.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private PromotionType type;

    private float value;

    private int usageLimit;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;

}
