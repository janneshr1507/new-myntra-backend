package com.jannesh.repository;

import com.jannesh.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    boolean existsByVendorIdAndPincode(UUID vendorId, String pincode);
    List<Warehouse> findByVendorId(UUID vendorId);
    boolean existsByVendorIdAndWarehouseId(UUID vendorId, UUID warehouseId);

}
