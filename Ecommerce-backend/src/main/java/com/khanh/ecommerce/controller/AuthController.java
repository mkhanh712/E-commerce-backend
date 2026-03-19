package com.khanh.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.khanh.ecommerce.DTO.LoginRequestDTO;
import com.khanh.ecommerce.repository.UserRepository;
import com.khanh.ecommerce.security.JwtTokenProvider;
import com.khanh.ecommerce.entity.Role;
import com.khanh.ecommerce.entity.User;

@RestController
@RequestMapping("/api/public")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request){
		Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
		if(userOpt.isEmpty() ||
			!passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
			return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password"));
		}
		User user = userOpt.get();
		List<String> roleNames = user.getRoles()
				.stream()
				.map(Role::getName)
				.collect(Collectors.toList());
		String token = jwtTokenProvider.generateToken(user.getUsername(), roleNames);
		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("username", user.getUsername());
		response.put("roles", roleNames);
		return ResponseEntity.ok(response);
		
	}
	
}
