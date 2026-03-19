package com.khanh.ecommerce.service;

import com.khanh.ecommerce.DTO.RegisterRequestDTO;
import com.khanh.ecommerce.DTO.UserDTO;
import com.khanh.ecommerce.DTO.UserUpdateDTO;
import com.khanh.ecommerce.entity.User;

public interface UserService {
	User registerUser(RegisterRequestDTO dto);
	UserDTO getCurrentUser(String username);
	UserDTO updateCurrentUser(String username, UserUpdateDTO dto);
}
