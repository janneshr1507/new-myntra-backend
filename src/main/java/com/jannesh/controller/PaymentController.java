package com.jannesh.controller;

import com.jannesh.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{cartId}")
    public ResponseEntity<?> processPayment(@PathVariable("cartId") UUID cartId) {
        return new ResponseEntity<>(paymentService.processPayment(cartId), HttpStatus.OK);
    }
}
