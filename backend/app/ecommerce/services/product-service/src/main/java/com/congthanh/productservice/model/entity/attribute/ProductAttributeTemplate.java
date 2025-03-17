//package com.congthanh.productservice.model.entity.attribute;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "product_attribute_template")
//public class ProductAttributeTemplate {
//
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "product_attribute", nullable = false)
//    private ProductAttribute productAttribute;
//
//    @ManyToOne
//    @JoinColumn(name = "product_template", nullable = false)
//    private ProductTemplate productTemplate;
//
//    private int displayOrder;
//
//}
