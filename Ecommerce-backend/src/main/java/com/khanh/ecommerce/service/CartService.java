package com.khanh.ecommerce.service;

import com.khanh.ecommerce.DTO.AddToCartRequest;
import com.khanh.ecommerce.DTO.CartResponseDTO;

public interface CartService {
	CartResponseDTO addProductToCart(String username, AddToCartRequest request);
}
