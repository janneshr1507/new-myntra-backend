package com.jannesh.util.mapper;

import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDTO toDTO(CartItem cartItem);
    List<CartItemDTO> toDTO(List<CartItem> cartItemList);
    CartItem toEntity(CartItemDTO cartItemDTO);
}
