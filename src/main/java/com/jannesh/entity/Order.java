package com.jannesh.entity;

import com.jannesh.util.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    private UUID orderId;

    @Column(nullable = false)
    private UUID customerId;

    private BigDecimal totalMRP = BigDecimal.ZERO;
    private BigDecimal totalDiscount = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @PrePersist
    public void onCreate() {
        this.orderId = UUID.randomUUID();
    }
}
