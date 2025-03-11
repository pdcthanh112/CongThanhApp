package com.congthanh.productservice.model.entity.variant;

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
@Table(name = "variant_option_value")
public class VariantOptionValue {

    @Id
    private Long id;

    @Column(name = "option_id", nullable = false)
    private Long optionId;

    @Column(nullable = false)
    private String value;

    @Column(name = "variant_id", nullable = false)
    private String variantId;

}
