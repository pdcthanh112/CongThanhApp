package com.congthanh.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherResponse {

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