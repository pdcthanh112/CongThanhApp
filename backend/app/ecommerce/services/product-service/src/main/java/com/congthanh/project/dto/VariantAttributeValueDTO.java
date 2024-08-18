package com.congthanh.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantAttributeValueDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private long attributeId;

    private String value;

    private String variantId;

}
