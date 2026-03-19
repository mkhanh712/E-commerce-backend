package com.khanh.ecommerce.serviceimp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.khanh.ecommerce.DTO.AddressDTO;
import com.khanh.ecommerce.entity.Address;
import com.khanh.ecommerce.entity.User;
import com.khanh.ecommerce.repository.AddressRepository;
import com.khanh.ecommerce.repository.UserRepository;
import com.khanh.ecommerce.service.AddressService;

@Service
public class AddressServiceImp implements AddressService {
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<AddressDTO> getAddressesOfUser(String username){
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!"));
		return addressRepository.findByUser(user)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public AddressDTO addAddressUser(String username, AddressDTO dto) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!"));
		Address address = new Address();
		address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipcode(dto.getZipcode());
        address.setUser(user);
        
        Address saved = addressRepository.save(address);
        return toDTO(saved);
	}
	
	@Override
	public AddressDTO updateAddressUser(String username, Long addressId, AddressDTO dto) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!"));
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address not found!"));
		if(address.getUser().getId() != user.getId()){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have permission change this address");
		}
		address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipcode(dto.getZipcode());
        return toDTO(addressRepository.save(address));
	}
	
	@Override
	public void deleteAddress(String username, Long addressId) {
		User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found!"));
        if (address.getUser().getId()!= user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền xóa địa chỉ này");
        }
        addressRepository.delete(address);
	}
	
	private AddressDTO toDTO(Address a) {
		return AddressDTO.builder()
				.id(a.getId())
				.street(a.getStreet())
                .city(a.getCity())
                .state(a.getState())
                .country(a.getCountry())
                .zipcode(a.getZipcode())
                .build();
	}
}
