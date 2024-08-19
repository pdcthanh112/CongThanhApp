package com.congthanh.project.entity;

import com.congthanh.project.constant.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
//@Data
@Getter
@Setter
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category", nullable = false)
    private Long category;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "subcategory", nullable = false)
    private Long subcategory;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "supplier")
    private String supplier;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "brand")
    private Long brand;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated
    private ProductStatus status;

    @Column(nullable = false, unique = true)
    private String slug;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    @ToString.Exclude
    private Set<ProductImage> image;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    @ToString.Exclude
    private Set<ProductAttributeValue> attribute;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    @ToString.Exclude
    private Set<ProductVariant> variant;

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
