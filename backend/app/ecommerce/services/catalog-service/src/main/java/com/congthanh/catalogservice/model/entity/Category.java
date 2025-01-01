package com.congthanh.catalogservice.model.entity;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "category")
public class Category  extends AbstractAuditEntity{

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