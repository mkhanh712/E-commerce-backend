package com.khanh.ecommerce.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
	@NotBlank(message = "Username không được đế trống")
	private String username;
	
	@NotBlank(message = "Password không được để trống")
	private String password;
}
