package com.jannesh.dto.payment;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jannesh.util.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@JsonPropertyOrder({"paymentId","cartId","orderId","amount","paymentStatus","createdAt"})
public class PaymentDTO {
    private UUID paymentId;
    private UUID cartId;
    private UUID orderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
