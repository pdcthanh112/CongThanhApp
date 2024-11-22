package com.congthanh.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDTO {

    private String id;

    private String code;

    private String type;

    private float value;

    private int usageLimit;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

    private long createdDate;

    private long updatedDate;

}
