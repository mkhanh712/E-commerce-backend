package com.khanh.ecommerce.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
	@NotBlank(message = "Username không được để trống")
	private String username;
	
	@NotBlank(message = "Password không được để trống")
	private String password;
	
	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không hợp lệ")
	private String email;
}
