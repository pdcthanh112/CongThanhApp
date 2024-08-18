package com.congthanh.project.model.request;

import com.congthanh.project.dto.ProductDTO;
import com.congthanh.project.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEvent {

    private String eventType;

    private ProductDTO product;

}
