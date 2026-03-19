package com.khanh.ecommerce.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
	private Long id;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipcode;
}
