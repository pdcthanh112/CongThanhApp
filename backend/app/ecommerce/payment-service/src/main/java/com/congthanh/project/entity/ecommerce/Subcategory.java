package com.congthanh.project.entity.ecommerce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subcategory")
public class Subcategory {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private String status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category", nullable = false)
  private Category category;

  @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Product> product;
}
