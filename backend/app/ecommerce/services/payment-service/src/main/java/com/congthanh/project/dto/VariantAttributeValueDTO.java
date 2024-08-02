package com.congthanh.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantAttributeValueDTO {

    private int id;

    private long attributeId;

    private String value;

    private String variantId;

}
