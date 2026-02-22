package com.jannesh.util.mapper;

import com.jannesh.dto.cart.CartDTO;
import com.jannesh.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDTO(Cart cart);
    Cart toEntity(CartDTO cart);
}
