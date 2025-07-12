package com.congthanh.catalogservice.model.entity;

import com.congthanh.catalogservice.constant.enums.TagStatus;
import com.congthanh.commonservice.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false, nullable = false)
//    private Instant createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private TagStatus status;

}
