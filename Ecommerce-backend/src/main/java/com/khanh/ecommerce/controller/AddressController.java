package com.khanh.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khanh.ecommerce.DTO.AddressDTO;
import com.khanh.ecommerce.service.AddressService;

@RestController
@RequestMapping("/api/user")
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/getAddress")
	public ResponseEntity<List<AddressDTO>> getAddresses(Authentication authentication){
		String username = authentication.getName();
		return ResponseEntity.ok(addressService.getAddressesOfUser(username));
	}
	
	@PostMapping("/addAddress")
	/**
	  {
	      "street": "123 Lê Lợi",
	      "city": "HCM",
	      "state": "TPHCM",
	      "country": "VN",
	      "zipcode": "70000"
    	}
	 */
    public ResponseEntity<AddressDTO> addAddress(Authentication authentication, @RequestBody AddressDTO dto) {
        String username = authentication.getName();
        return ResponseEntity.ok(addressService.addAddressUser(username, dto));
    }

    @PutMapping("/updateAddress/{id}")
    public ResponseEntity<AddressDTO> updateAddress(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody AddressDTO dto) {
        String username = authentication.getName();
        return ResponseEntity.ok(addressService.updateAddressUser(username, id, dto));
    }

    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<Void> deleteAddress(Authentication authentication, @PathVariable Long id) {
        String username = authentication.getName();
        addressService.deleteAddress(username, id);
        return ResponseEntity.noContent().build();
    }
}
