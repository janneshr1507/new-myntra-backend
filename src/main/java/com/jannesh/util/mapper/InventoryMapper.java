package com.jannesh.util.mapper;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryDTO toDTO (Inventory inventory);
    Inventory toEntity(InventoryDTO inventoryDTO);
}
