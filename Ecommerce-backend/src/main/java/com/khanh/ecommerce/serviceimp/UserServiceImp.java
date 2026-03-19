package com.khanh.ecommerce.serviceimp;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.khanh.ecommerce.DTO.AddressDTO;
import com.khanh.ecommerce.DTO.RegisterRequestDTO;
import com.khanh.ecommerce.DTO.UserDTO;
import com.khanh.ecommerce.DTO.UserUpdateDTO;
import com.khanh.ecommerce.entity.User;
import com.khanh.ecommerce.entity.Cart;
import com.khanh.ecommerce.entity.Role;
import com.khanh.ecommerce.repository.CartRepository;
import com.khanh.ecommerce.repository.RoleRepository;
import com.khanh.ecommerce.repository.UserRepository;
import com.khanh.ecommerce.service.UserService;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User registerUser(RegisterRequestDTO dto) {
		if(userRepository.findByUsername(dto.getUsername()).isPresent()){
			throw new RuntimeException("Username existed!");
		}
		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		Role roleUser = roleRepository.findByName("ROLE_USER")
				.orElseThrow(() -> new RuntimeException("Role ROLE_USER chưa tồn tại!"));
		List<Role> roles = new ArrayList<>();
		roles.add(roleUser);
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(encodedPassword);
		user.setEmail(dto.getEmail());
		user.setRoles(roles);
		User savedUser = userRepository.save(user); //lưu user vào db trước
		Cart cart = new Cart();  
		cart.setUser(savedUser); //object cart được set thông tin của user đã lưu trước đó
		Cart savedCart = cartRepository.save(cart); //lưu cart vào db sau khi đã có user 
		savedUser.setCart(savedCart); //gắn cart vào obj savedCart vừa tạo để trả ra bên dưới
		return savedUser;//trả ra file json sau khi đky chứa các thông tin ở trên trong obj savedUser
	}
	
	@Override
	public UserDTO getCurrentUser(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
		 List<AddressDTO> addresses = user.getAddresses() != null
	                ? user.getAddresses().stream()
	                        .map(a -> new AddressDTO(
	                        		a.getId(),
	                        		a.getStreet(), 
	                        		a.getCity(),
	                                a.getState(), 
	                                a.getCountry(), 
	                                a.getZipcode()
	                                )
	                        	)
	                        .collect(Collectors.toList())
	                : List.of();;
        return new UserDTO(
        		user.getId(), 
        		user.getUsername(), 
        		user.getEmail(), 
        		addresses,
        		user.getCart()
        		);
    }
	
	@Override
    public UserDTO updateCurrentUser(String username, UserUpdateDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);
        return getCurrentUser(username);
    }
}
