//package com.congthanh.productservice.model.entity.attribute;
//
//import com.congthanh.productservice.model.entity.AbstractAuditEntity;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
//@Builder
//@Table(name = "product_template")
//public class ProductTemplate extends AbstractAuditEntity {
//
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @OneToMany(mappedBy = "productTemplate", cascade = {CascadeType.PERSIST})
//    @Builder.Default
//    private List<ProductAttributeTemplate> productAttributeTemplates = new ArrayList<>();
//
//}
