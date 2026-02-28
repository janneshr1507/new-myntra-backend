package com.jannesh.service;

import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.dto.order.OrderDTO;
import com.jannesh.dto.payment.PaymentDTO;
import com.jannesh.dto.cart.CartDTO;
import com.jannesh.entity.Order;
import com.jannesh.entity.OrderItem;
import com.jannesh.entity.Payment;
import com.jannesh.repository.PaymentRepository;
import com.jannesh.util.enums.CartStatus;
import com.jannesh.util.enums.OrderStatus;
import com.jannesh.util.enums.PaymentStatus;
import com.jannesh.util.mapper.PaymentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepo;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final PaymentMapper mapper;

    @Transactional
    public PaymentDTO processPayment(UUID cartId) {
        CartDTO cartDTO = cartService.fetchCartDetails(cartId);

        Payment payment = new Payment();
        payment.setCartId(cartId);
        payment.setAmount(cartDTO.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        cartDTO.setCartStatus(CartStatus.CLOSED);
        cartService.updateCartStatus(cartDTO);

        Order order = new Order();
        order.setCustomerId(cartDTO.getCustomerId());
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalMRP(cartDTO.getTotalMRP());
        order.setTotalDiscount(cartDTO.getTotalDiscount());
        order.setTotalAmount(cartDTO.getTotalAmount());
        OrderDTO savedOrder = orderService.createOrder(order);

        List<CartItemDTO> cartItemListDTO = cartItemService.fetchCartItemDetailsByCartId(cartId);
        for (CartItemDTO cartItemDTO: cartItemListDTO) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getOrderId());
            mapToOrder(orderItem, cartItemDTO);
            orderItemService.createOrderItem(orderItem);
        }

        payment.setOrderId(savedOrder.getOrderId());
        return mapper.toDTO(paymentRepo.save(payment));
    }

    private void mapToOrder(OrderItem orderItem, CartItemDTO cartItemDTO) {
        orderItem.setItemId(cartItemDTO.getItemId());
        orderItem.setQuantity(cartItemDTO.getQuantity());
        orderItem.setAmount(cartItemDTO.getAmount());
    }
}
