package com.jannesh.service;

import com.jannesh.dto.orderItem.OrderItemDTO;
import com.jannesh.entity.OrderItem;
import com.jannesh.repository.OrderItemRepository;
import com.jannesh.util.mapper.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepo;
    private final OrderItemMapper mapper;

    public void createOrderItem(OrderItem orderItem) {
        orderItemRepo.save(orderItem);
    }

    public List<OrderItemDTO> fetchOrderItemDetails(UUID orderId) {
        List<OrderItem> orderItemList = orderItemRepo.findByOrderId(orderId);
        return mapper.toDTO(orderItemList);
    }
}
