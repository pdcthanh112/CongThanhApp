package com.congthanh.project.entity.ecommerce;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String product;

    @Column(name = "image_path", length = 1500)
    private String imagePath;

    private String alt;

    @Column(name = "is_default")
    @JsonProperty("isDefault")
    private boolean isDefault;

}
