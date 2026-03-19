package com.khanh.ecommerce.service;

import com.khanh.ecommerce.DTO.OrderRequestDTO;
import com.khanh.ecommerce.DTO.OrderResponseDTO;

public interface OrderService {
	OrderResponseDTO placeOrder(String username, OrderRequestDTO dto);
}
