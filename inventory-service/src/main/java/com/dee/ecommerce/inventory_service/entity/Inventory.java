package com.dee.ecommerce.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Version       // Prevents race conditions on concurrent updates
    private Long version;
    /*
    ====>  Optimistic lock (@Version) — no locking on read, detects conflict at write time.
     Best as a safety net for all other operations like createOrUpdateStock.
     */
}
