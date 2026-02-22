package com.jannesh.util.mapper;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDTO toDTO(Item item);
    Item toEntity(ItemDTO itemDTO);
    Item toEntity(SaveItemDTO requestDTO);

}
