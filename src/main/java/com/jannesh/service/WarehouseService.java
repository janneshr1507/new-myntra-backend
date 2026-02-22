package com.jannesh.service;

import com.jannesh.dto.warehouse.SaveWarehouseDTO;
import com.jannesh.dto.warehouse.WarehouseDTO;
import com.jannesh.entity.Warehouse;
import com.jannesh.repository.WarehouseRepository;
import com.jannesh.util.mapper.WarehouseMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepo;
    private final VendorService vendorService;
    private final WarehouseMapper mapper;

    public WarehouseDTO fetchWarehouseDetails(UUID warehouseId) {
        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse Not Found"));
        return mapper.toDTO(warehouse);
    }

    public WarehouseDTO createWarehouse(SaveWarehouseDTO requestDTO) {
        if(warehouseRepo.existsByVendorIdAndPincode(requestDTO.getVendorId(), requestDTO.getPincode()))
            throw new RuntimeException("Warehouse already exists on that pincode");

        if(!vendorService.existsByVendorId(requestDTO.getVendorId()))
            throw new EntityNotFoundException("Vendor Not Found");

        Warehouse warehouse = mapper.toEntity(requestDTO);
        warehouse.setVendorId(requestDTO.getVendorId());

        return mapper.toDTO(warehouseRepo.save(warehouse));
    }

    public List<WarehouseDTO> fetchWarehouseDetailsByVendorId(UUID vendorId) {
        List<Warehouse> warehouseList = warehouseRepo.findByVendorId(vendorId);
        if(warehouseList.isEmpty()) throw new RuntimeException("No warehouse available for that vendorId");

        List<WarehouseDTO> warehouseDTOList = new ArrayList<>();
        for(Warehouse warehouse: warehouseList) {
            WarehouseDTO warehouseDTO = mapper.toDTO(warehouse);
            warehouseDTOList.add(warehouseDTO);
        }

        return warehouseDTOList;
    }
}
