package com.khanh.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khanh.ecommerce.entity.Address;
import com.khanh.ecommerce.entity.User;

public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findByUser(User user);
}
