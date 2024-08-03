package com.congthanh.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "product_variant_image")
public class ProductVariantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant")
    @JsonBackReference
    private ProductVariant variant;

    @Column(name = "image_path", length = 1500)
    private String imagePath;

    private String alt;

    @Column(name = "is_default")
    @JsonProperty("isDefault")
    private boolean isDefault;

}
