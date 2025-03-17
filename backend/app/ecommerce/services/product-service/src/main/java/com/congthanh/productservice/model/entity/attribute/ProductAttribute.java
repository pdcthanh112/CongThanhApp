package com.congthanh.productservice.model.entity.attribute;

import com.congthanh.productservice.model.entity.AbstractAuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "product_attribute")
public class ProductAttribute extends AbstractAuditEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @ManyToOne
//    @JoinColumn(name = "product_attribute_group")
//    private ProductAttributeGroup productAttributeGroup;
//
//    @OneToMany(mappedBy = "productAttribute")
//    @JsonIgnore
//    private List<ProductAttributeTemplate> productAttributeTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "productAttribute")
    private List<ProductAttributeValue> attributeValues = new ArrayList<>();

}
