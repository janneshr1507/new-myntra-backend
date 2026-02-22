package com.jannesh.service;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.entity.Item;
import com.jannesh.repository.ItemRepository;
import com.jannesh.util.mapper.ItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;
    private final ItemMapper mapper;

    public ItemDTO fetchItemDetails(UUID itemId) {
        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item Not Found"));
        return mapper.toDTO(item);
    }

    public ItemDTO createItem(SaveItemDTO requestDTO) {
        if(!vendorService.existsByVendorId(requestDTO.getVendorId()))
            throw new EntityNotFoundException("Vendor Not Available");

        if(!warehouseService.existsByVendorIdAndWarehouseId(requestDTO.getVendorId(), requestDTO.getWarehouseId()))
            throw new EntityNotFoundException("Warehouse Not Found");

        Item item = mapper.toEntity(requestDTO);
        item.setVendorId(requestDTO.getVendorId());
        item.setWarehouseId(requestDTO.getWarehouseId());

        return mapper.toDTO(itemRepo.save(item));
    }

    public ItemDTO updateDiscountDetails(UUID itemId, BigDecimal discount) {
        Item item = itemRepo.findById(itemId)
                        .orElseThrow(() -> new EntityNotFoundException("Item Not Found"));

        item.setDiscount(discount);
        return mapper.toDTO(itemRepo.save(item));
    }

    public boolean existsByItemId(UUID itemId) {
        return itemRepo.existsById(itemId);
    }
}
