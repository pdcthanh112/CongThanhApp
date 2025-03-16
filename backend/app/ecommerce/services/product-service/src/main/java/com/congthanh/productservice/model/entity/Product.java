package com.congthanh.productservice.model.entity;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.entity.attribute.ProductAttributeValue;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
@NamedEntityGraph(name = "Product.WithAll", attributeNodes = {
        @NamedAttributeNode("image"),
        @NamedAttributeNode("variant"),
        @NamedAttributeNode("attribute")
}, subgraphs = {})
public class Product {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductCategory> category = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "supplier")
    private String supplier;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "brand")
    private Long brand;

    @Column(columnDefinition = "text")
    private String description;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(nullable = false, unique = true)
    private String slug;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    @ToString.Exclude
    @Builder.Default
    private Set<ProductImage> image = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    @ToString.Exclude
    @Builder.Default
    private Set<ProductAttributeValue> attribute = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    @ToString.Exclude
    @Builder.Default
    private Set<ProductVariant> variant = new HashSet<>();

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<Review> review;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<CartItem> cartItems;
//
//    @ManyToMany(mappedBy = "product")
//    @JsonIgnore
//    @JsonBackReference
//    private Set<Wishlist> wishlist;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<OrderDetail> orderDetail;
//
//    @ManyToMany
//    @JoinTable(name = "product_tag",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
//    private Set<Tag> tag;

}
