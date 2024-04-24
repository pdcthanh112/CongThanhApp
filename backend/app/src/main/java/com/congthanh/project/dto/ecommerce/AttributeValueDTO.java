package com.congthanh.project.dto.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValueDTO {

    private long id;

    private ProductAttributeDTO attribute;

    private String product;

    private String value;

}
