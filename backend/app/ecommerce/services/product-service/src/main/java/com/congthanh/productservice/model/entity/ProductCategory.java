package com.congthanh.productservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory {

    @Id
    private Long id;

    @Column(name = "product_id", nullable = false, updatable = false)
    private String productId;

    @Column(name = "category_id", nullable = false, updatable = false)
    private String categoryId;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;
}
