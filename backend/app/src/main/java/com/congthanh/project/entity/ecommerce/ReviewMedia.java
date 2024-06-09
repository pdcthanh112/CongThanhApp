package com.congthanh.project.entity.ecommerce;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "review_media")
public class ReviewMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review", nullable = false)
    @JsonManagedReference
    private Review review;

}
