package com.congthanh.project.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_variant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", nullable = false)
    @JsonManagedReference
    @ToString.Exclude
    private Product product;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, name = "sku")
    private String sku;

    @Column(nullable = false, unique = true, name = "gtin")
    private String gtin;

    @Column(name = "price", precision = 19, scale = 4)
    private BigDecimal price;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private Set<ProductVariantImage> image;

}
