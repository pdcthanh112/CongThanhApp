package com.congthanh.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    private Long id;

    private String variantId;

    private String supplierId;

    private int stock;

}
