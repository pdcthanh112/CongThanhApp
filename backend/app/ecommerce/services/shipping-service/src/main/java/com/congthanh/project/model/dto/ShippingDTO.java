package com.congthanh.project.model.dto;

import com.congthanh.project.constant.enums.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingDTO {

    private Long id;

    private Long order;

    private String customer;

    private String shippingAddress;

    private ShippingStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
