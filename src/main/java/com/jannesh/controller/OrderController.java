package com.jannesh.controller;

import com.jannesh.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable("orderId") UUID orderId) {
        return new ResponseEntity<>(orderService.fetchOrderDetails(orderId), HttpStatus.OK);
    }
}
