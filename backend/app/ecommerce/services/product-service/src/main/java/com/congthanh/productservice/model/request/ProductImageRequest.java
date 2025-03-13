package com.congthanh.productservice.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImageRequest {
    private Long id;
    private String imagePath;
    private int displayOrder;
}
