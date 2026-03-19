package com.khanh.ecommerce.DTO;

import java.util.List;

import com.khanh.ecommerce.entity.Cart;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Long id;
	private String username;
	private String email;
	private List<AddressDTO> addresses;
	private Cart cart;
}
