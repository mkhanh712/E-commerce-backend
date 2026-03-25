package com.khanh.ecommerce.controller;

import com.khanh.ecommerce.DTO.AddToCartRequest;
import com.khanh.ecommerce.DTO.CartResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.khanh.ecommerce.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/user/cart/add")
    /**
     * {
     * "variantProductId": x,
     * "quantity": x
     * }
     */
    public CartResponseDTO addToCart(@RequestBody AddToCartRequest request, Authentication authentication) {
        String username = authentication.getName();
        return cartService.addProductToCart(username, request);
    }
}
