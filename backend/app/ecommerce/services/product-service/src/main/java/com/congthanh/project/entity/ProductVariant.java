package com.congthanh.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
//@Data
@Getter
@Setter
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

    @Column(name = "price", precision = 19, scale = 4)
    private BigDecimal price;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private Set<ProductVariantImage> image;

//    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<CartItem> cartItems;
//
//    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<Review> review;

}
