package com.jannesh.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    private UUID orderItemId;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private UUID itemId;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.orderItemId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

}
