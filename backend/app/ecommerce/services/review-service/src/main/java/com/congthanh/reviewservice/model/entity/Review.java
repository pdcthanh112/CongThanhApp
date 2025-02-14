package com.congthanh.reviewservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    private Long id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String author;

    private String product;

    private String variant;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference
    private List<ReviewMedia> reviewMedia;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

}
