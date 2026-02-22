package com.jannesh.entity;

import com.jannesh.util.enums.CartStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class Cart {
    @Id
    private UUID cartId;

    @Column(nullable = false)
    private UUID customerId;

    private BigDecimal totalMRP = BigDecimal.ZERO;
    private BigDecimal totalDiscount = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartStatus cartStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.cartId = UUID.randomUUID();
        this.cartStatus = CartStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
