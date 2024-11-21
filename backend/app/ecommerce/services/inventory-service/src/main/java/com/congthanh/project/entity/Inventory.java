package com.congthanh.project.entity;

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
@Table(name = "inventory")
public class Inventory {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private int quantity;

    private String warehouse;

}
