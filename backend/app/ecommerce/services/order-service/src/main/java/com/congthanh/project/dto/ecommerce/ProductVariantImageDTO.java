package com.congthanh.project.dto.ecommerce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantImageDTO {

    private long id;

    private String variant;

    private String imagePath;

    private String alt;

    @JsonProperty("isDefault")
    private boolean isDefault;

}
