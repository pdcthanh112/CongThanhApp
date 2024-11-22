package com.congthanh.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingLabelDTO {

    private String id;

    private String shippingId;

    private String barcode;

    private Instant createdAt;

    private String labelUrl;

}
