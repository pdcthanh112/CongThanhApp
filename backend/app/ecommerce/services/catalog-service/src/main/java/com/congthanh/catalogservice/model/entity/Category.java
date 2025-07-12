package com.congthanh.catalogservice.model.entity;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import com.congthanh.commonservice.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "categories")
public class Category extends BaseEntity {

    @Id
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
}