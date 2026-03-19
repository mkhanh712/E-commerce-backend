package com.khanh.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
