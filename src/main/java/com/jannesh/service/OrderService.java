package com.jannesh.service;

import com.jannesh.dto.order.OrderDTO;
import com.jannesh.dto.orderItem.OrderItemDTO;
import com.jannesh.entity.Order;
import com.jannesh.repository.OrderRepository;
import com.jannesh.util.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemService orderItemService;
    private final OrderMapper mapper;

    public Order createOrder(Order order) {
        orderRepo.save(order);
        return order;
    }

    @Transactional
    public OrderDTO fetchOrderDetails(UUID orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

        OrderDTO orderDTO = mapper.toDTO(order);

        List<OrderItemDTO> orderItemDTOList = orderItemService.fetchOrderItemDetails(order.getOrderId());
        orderDTO.setOrderItemList(orderItemDTOList);
        return orderDTO;
    }
}
