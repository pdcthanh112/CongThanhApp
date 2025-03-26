package com.congthanh.productservice.model.entity;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
//@NamedEntityGraph(name = "Product.WithAll", attributeNodes = {
//        @NamedAttributeNode("image"),
//        @NamedAttributeNode("attribute")
//}, subgraphs = {})
public class Product {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String slug;

    @Column( unique = true)
    private String sku;

    private String gtin;

    @Column(name = "has_variant", nullable = false)
    private boolean hasVariant;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Product parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Builder.Default
    private List<Product> variant = new ArrayList<>();

    @Column(name = "price", precision = 19, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<ProductCategory> category = new ArrayList<>();

    private String supplier;

    private Long brand;

    @Column(name = "is_featured")
    private boolean isFeatured;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    @JsonBackReference
//    @ToString.Exclude
//    @Builder.Default
    private Set<ProductImage> image = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    @JsonBackReference
//    @ToString.Exclude
//    @Builder.Default
    private Set<ProductAttributeValue> attribute = new HashSet<>();

}
