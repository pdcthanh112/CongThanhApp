package com.congthanh.inventoryservice.model.entity;

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
@Table(name = "inventories")
public class Inventory {

    @Id
    private Long id;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private int stock;

    @Column(name = "warehouse_id", nullable = false)
    private String warehouse;

}
