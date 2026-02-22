package com.jannesh.service;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.dto.inventory.SaveInventoryDTO;
import com.jannesh.entity.Inventory;
import com.jannesh.repository.InventoryRepository;
import com.jannesh.util.mapper.InventoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final ItemService itemService;
    private final InventoryMapper mapper;

    public InventoryDTO fetchInventoryDetailsByItemId(UUID itemId) {
        Inventory inventory = inventoryRepo.findByItemId(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory Not Found"));
        return mapper.toDTO(inventory);
    }

    public InventoryDTO createInventory(SaveInventoryDTO requestDTO) {
        if(!itemService.existsByItemId(requestDTO.getItemId()))
            throw new EntityNotFoundException("Item Not Found");

        Inventory inventory = inventoryRepo.findByItemId(requestDTO.getItemId())
                        .orElseGet(Inventory::new);

        inventory.setItemId(requestDTO.getItemId());
        inventory.setAvailableQty((inventory.getAvailableQty() == null ? 0L : inventory.getAvailableQty())
                + requestDTO.getAvailableQty());

        return mapper.toDTO(inventoryRepo.save(inventory));
    }

    public void reserveInventoryForCart(UUID itemId, Long quantity) {
        Inventory inventory = mapper.toEntity(fetchInventoryDetailsByItemId(itemId));
        if(quantity > inventory.getAvailableQty()) {
            throw new RuntimeException("Stock Not Available");
        }

        inventory.setAvailableQty(inventory.getAvailableQty() - quantity);
        inventory.setReservedQty(inventory.getReservedQty() + quantity);
        inventoryRepo.save(inventory);
    }

    public void releaseInventoryFromCart(UUID itemId, Long quantity) {
        Inventory inventory = mapper.toEntity(fetchInventoryDetailsByItemId(itemId));

        inventory.setAvailableQty(inventory.getAvailableQty() + quantity);
        inventory.setReservedQty(inventory.getReservedQty() - quantity);
        inventoryRepo.save(inventory);
    }

}
