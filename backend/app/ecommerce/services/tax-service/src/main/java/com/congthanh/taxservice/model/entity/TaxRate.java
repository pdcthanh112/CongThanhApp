package com.congthanh.taxservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "tax_rate")
public class TaxRate extends AbstractAuditEntity{

    @Id
    private Long id;

    @Column(nullable = false)
    private Double rate;

    @Column(length = 25)
    private String zipCode;

    @Column
    private Long stateOrProvinceId;

    @Column(nullable = false)
    private Long countryId;

    @ManyToOne
    @JoinColumn(name = "tax_class_id", nullable = false)
    private TaxClass taxClass;
}
