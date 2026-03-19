package com.khanh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.khanh.ecommerce.DTO.OrderRequestDTO;
import com.khanh.ecommerce.DTO.OrderResponseDTO;
import com.khanh.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/user/orders")
public class OrderController {

    @Autowired private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(Authentication authentication, @RequestBody OrderRequestDTO request) {
        String username = authentication.getName();
        OrderResponseDTO response = orderService.placeOrder(username, request);
        return ResponseEntity.ok(response);
    }
}
