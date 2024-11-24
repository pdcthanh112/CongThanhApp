package com.congthanh.project.model.entity;

import com.congthanh.project.constant.enums.TagStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "tag")
public class Tag extends AbstractAuditEntity {

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
