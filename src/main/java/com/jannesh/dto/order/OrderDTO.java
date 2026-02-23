package com.jannesh.dto.order;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jannesh.dto.orderItem.OrderItemDTO;
import com.jannesh.util.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@JsonPropertyOrder({"orderId","customerId","totalMRP","totalDiscount","totalAmount","orderItemList","orderStatus"})
public class OrderDTO {
    private UUID orderId;
    private UUID customerId;
    private BigDecimal totalMRP;
    private BigDecimal totalDiscount;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    public List<OrderItemDTO> orderItemList = new ArrayList<>();
}
