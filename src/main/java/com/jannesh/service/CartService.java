package com.jannesh.service;

import com.jannesh.dto.cart.AddItemDTO;
import com.jannesh.dto.cart.CartDTO;
import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.dto.item.ItemDTO;
import com.jannesh.entity.Cart;
import com.jannesh.repository.CartRepository;
import com.jannesh.util.enums.CartStatus;
import com.jannesh.util.mapper.CartMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final CartItemService cartItemService;
    private final CartMapper mapper;

    public CartDTO createCart(UUID customerId) {
        if(!customerService.existsByCustomerId(customerId))
            throw new EntityNotFoundException("Customer Not Found");

        Cart cart = new Cart();
        cart.setCustomerId(customerId);

        return mapper.toDTO(cartRepo.save(cart));
    }

    /*public Cart fetchCartByCustomerId(UUID customerId) {
        return cartRepo.findByCustomer_CustomerIdAndCartStatus(customerId, CartStatus.OPEN)
                .orElseGet(() -> createCart((customerId)));
    }*/

    @Transactional
    public CartDTO addItemToCart(AddItemDTO addItemDTO) {
        //Check Customer Status
        if(!customerService.existsByCustomerId(addItemDTO.getCustomerId()))
            throw new EntityNotFoundException("Customer Not Found");

        //Check Item Status
        ItemDTO itemDTO = itemService.fetchItemDetails(addItemDTO.getItemId());

        //Check Inventory Status & Reserve Stock if available
        inventoryService.reserveInventoryForCart(addItemDTO.getItemId(), addItemDTO.getQuantity());

        //Fetch existing open cart for the customer or create new
        Cart cart = cartRepo.findByCustomerIdAndCartStatus(addItemDTO.getCustomerId(), CartStatus.OPEN)
                .orElseGet(() -> mapper.toEntity(createCart(addItemDTO.getCustomerId())));

        //Prepare Item (If Item already present in the Cart then update quantity)
        CartItemDTO cartItemDTO = cartItemService.fetchCartItemDetailsByCartIdAndItemId(cart.getCartId(), addItemDTO.getItemId());
        if(cartItemDTO.getCartItemId() == null) {
            cartItemDTO.setCartId(cart.getCartId());
            cartItemDTO.setItemId(itemDTO.getItemId());
            cartItemDTO.setQuantity(addItemDTO.getQuantity());
        } else {
            cartItemDTO.setQuantity(cartItemDTO.getQuantity() + addItemDTO.getQuantity());
        }

        //Calculating amount by multiplying sellingPrice and quantity.
        cartItemDTO.setAmount(itemDTO.getSellingPrice().multiply(BigDecimal.valueOf(cartItemDTO.getQuantity())));

        //Add Item to the Cart
        CartItemDTO savedCartItemDTO = cartItemService.createCartItem(cartItemDTO);

        //Calculate Cart Amount
        calculateCartAmountWhenItemAdded(cart, addItemDTO.getQuantity(), savedCartItemDTO.getItemId());

        //Save Cart in the DB
        Cart updatedCart = cartRepo.save(cart);

        //Prepare for Response
        CartDTO cartDTO = mapper.toDTO(updatedCart);
        List<CartItemDTO> cartItemDTOList = cartItemService.fetchCartItemDetailsByCartId(updatedCart.getCartId());
        cartDTO.setCartItemList(cartItemDTOList);
        return cartDTO;
    }

    /*public CartDTO removeItemFromCart(UUID cartItemId) {
        CartItem cartItem = cartItemService.fetchCartItemByCartItemId(cartItemId);

        if(cartItem.getCart().getCartStatus() == CartStatus.CLOSED)
            throw new RuntimeException("You cannot remove item from closed cart");

        cartItemService.deleteCartItem(cartItem.getCartItemId());

        calculateCartAmountWhenItemRemove(cartItem.getCart(), cartItem);
        cartRepo.save(cartItem.getCart());

        inventoryService.releaseInventoryFromCart(cartItem.getItem().getItemId(), cartItem.getQuantity());

        return fetchCartDTOByCartId(cartItem.getCart().getCartId());
    }

    public Cart fetchCartByCartIdWithCartItemDetails(UUID cartId) {
        return cartRepo.findByCartId(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));
    }

    public Cart fetchCartByCartId(UUID cartId) {
        return cartRepo.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));
    }

    public Cart updateCartDetails(Cart cart) {
        return cartRepo.save(cart);
    }

    @Transactional
    public CartDTO fetchCartDTOByCartId(UUID cartId) {
        Cart cart = fetchCartByCartIdWithCartItemDetails(cartId);
        return mapper.toDTO(cart);
    }*/

    public void calculateCartAmountWhenItemAdded(Cart cart, Long quantity, UUID itemId) {
        ItemDTO itemDTO = itemService.fetchItemDetails(itemId);

        BigDecimal cartItemMRP = itemDTO.getActualPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal cartItemAmount = itemDTO.getSellingPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal cartItemDiscount = cartItemMRP.subtract(cartItemAmount);

        BigDecimal totalMRP = cart.getTotalMRP().add(cartItemMRP);
        BigDecimal totalAmount = cart.getTotalAmount().add(cartItemAmount);
        BigDecimal totalDiscount = totalMRP.subtract(totalAmount);

        cart.setTotalMRP(totalMRP);
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalAmount(totalAmount);
    }

    public CartDTO fetchCartDetails(UUID cartId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));
        return mapper.toDTO(cart);
    }

    public void updateCartStatus(CartDTO cartDTO) {
        Cart cart = cartRepo.findById(cartDTO.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));

        cart.setCartStatus(cartDTO.getCartStatus());
        cartRepo.save(cart);
    }

    /*public void calculateCartAmountWhenItemRemove(Cart cart, CartItem cartItem) {
        BigDecimal totalMRP = cart.getTotalMRP().subtract(cartItem.getItem().getActualPrice());
        BigDecimal totalDiscount = cart.getTotalDiscount().subtract(cartItem.getItem().getActualPrice().subtract(cartItem.getItem().getSellingPrice()));
        BigDecimal totalAmount = cart.getTotalAmount().subtract(cartItem.getItem().getSellingPrice());

        cart.setTotalMRP(totalMRP);
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalAmount(totalAmount);
    }*/
}
