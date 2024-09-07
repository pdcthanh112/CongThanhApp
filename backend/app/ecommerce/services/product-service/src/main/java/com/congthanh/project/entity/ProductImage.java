package com.congthanh.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", nullable = false)
    @JsonManagedReference
    private Product product;

    @Column(name = "image_path", length = 1500)
    private String imagePath;

    private String alt;

    @Column(name = "is_default")
    @JsonProperty("isDefault")
    private boolean isDefault;

}
