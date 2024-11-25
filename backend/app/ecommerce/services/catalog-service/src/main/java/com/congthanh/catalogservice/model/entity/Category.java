package com.congthanh.catalogservice.model.entity;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "category")
public class Category  extends AbstractAuditEntity{

    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    private String description;

    private String image;

    @Column(name = "parent_id")
    private String parentId;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

//    private Instant createdAt;
//
//    private Instant updatedAt;

}