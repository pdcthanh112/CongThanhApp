package com.congthanh.project.entity.ecommerce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(length = 1500)
    private String avatar;

    @Column(length = 1500)
    private String background;

    private String domain;

//    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<Product> product;

}
