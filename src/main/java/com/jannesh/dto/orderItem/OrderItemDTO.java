package com.jannesh.dto.orderItem;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@JsonPropertyOrder({"orderItemId","orderId","itemId","quantity","amount","createdAt"})
public class OrderItemDTO {
    private UUID orderItemId;
    private UUID orderId;
    private UUID itemId;
    private Long quantity;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
