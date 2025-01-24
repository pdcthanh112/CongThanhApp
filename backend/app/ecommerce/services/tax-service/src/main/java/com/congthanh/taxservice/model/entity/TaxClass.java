package com.congthanh.taxservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "tax_class")
public class TaxClass extends AbstractAuditEntity{

    @Id
    private Long id;

    @Column(nullable = false, length = 450)
    private String name;

}
