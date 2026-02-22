package com.jannesh.service;

import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.CartItem;
import com.jannesh.repository.CartItemRepository;
import com.jannesh.util.mapper.CartItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepo;
    private final CartItemMapper mapper;

    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = mapper.toEntity(cartItemDTO);
        return mapper.toDTO(cartItemRepo.save(cartItem));
    }

    public CartItemDTO fetchCartItemDetails(UUID cartItemId) {
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem Not Found"));
        return mapper.toDTO(cartItem);
    }

    public CartItemDTO fetchCartItemDetailsByCartIdAndItemId(UUID cartId, UUID itemId) {
        CartItem cartItem = cartItemRepo.findByCartIdAndItemId(cartId, itemId)
                .orElseGet(CartItem::new);
        return mapper.toDTO(cartItem);
    }

    public List<CartItemDTO> fetchCartItemDetailsByCartId(UUID cartId) {
        return mapper.toDTO(cartItemRepo.findByCartId(cartId));
    }

    /*public List<CartItemDTO> fetchCartItemDTO(UUID cartId) {
        List<CartItem> cartItemList = fetchCartItem(cartId);

        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for(CartItem cartItem: cartItemList) {
            CartItemDTO cartItemDTO = mapper.toDTO(cartItem);
            cartItemDTOList.add(cartItemDTO);
        }

        return cartItemDTOList;
    }

    public void deleteCartItem(UUID cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }*/

}
