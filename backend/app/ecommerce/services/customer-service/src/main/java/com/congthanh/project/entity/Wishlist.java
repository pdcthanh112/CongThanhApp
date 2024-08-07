package com.congthanh.project.entity;

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
@Table(name = "wishlist")
public class Wishlist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customer;

  @ManyToMany
  @JoinTable(name = "wishlist_item",
          joinColumns = @JoinColumn(name = "wishlist_id"),
          inverseJoinColumns = @JoinColumn(name = "product_id"))
  @JsonIgnore
  private Set<Product> product;

}
