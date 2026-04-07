package com.dee.ecommerce.inventory_service.repository;

import com.dee.ecommerce.inventory_service.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    //Used by reduceStock — locks the row at DB level during the transaction
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.productId = :productId")
    Optional<Inventory> findByProductIdForUpdate(@Param("productId") Long productId);

    /*
     ====> Pessimistic lock (PESSIMISTIC_WRITE) — locks the DB row immediately on read.
     Other transactions trying to read-for-update on the same row wait.
     Best when conflicts are very likely, like reduceStock during a flash sale.
     ====>  Optimistic lock (@Version) — no locking on read, detects conflict at write time.
     Best as a safety net for all other operations like createOrUpdateStock.

     @Version is optimistic — it assumes conflicts are rare, and handles them after the fact.
     @Lock is pessimistic — it assumes conflicts are likely, and prevents them upfront.
     */


}
