package com.congthanh.reviewservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String customerId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product", nullable = false)
//    @JsonIgnore
    private String product;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_variant")
//    @JsonIgnore
    private String variant;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    private List<ReviewMedia> reviewMedia;

    @Column(name = "created_at")
    private long createdAt;

}
