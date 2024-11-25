package com.congthanh.supplierservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeValueRequest {

    private Long attribute;

    private String product;

    private String value;

}
