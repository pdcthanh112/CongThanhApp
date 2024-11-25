package com.congthanh.supplierservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantImageResponse {

    private Long id;

    private String variant;

    private String imagePath;

    private String alt;

    @JsonProperty("isDefault")
    private boolean isDefault;

}
