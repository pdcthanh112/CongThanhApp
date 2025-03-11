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
@Table(name = "variant_option")
public class VariantOption {

    @Id
    private Long id;

    private String name;

}
