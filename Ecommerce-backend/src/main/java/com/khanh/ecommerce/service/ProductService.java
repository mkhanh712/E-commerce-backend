package com.khanh.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.khanh.ecommerce.DTO.ProductDTO;
import com.khanh.ecommerce.DTO.ProductRequestDTO;
import com.khanh.ecommerce.entity.Product;

public interface ProductService {
	Page<ProductDTO> getProductsByCategory(Long categoryId, Pageable pageable);
	ProductDTO getProductById(Long id);
	Product createProduct(ProductRequestDTO dto);
	Product updateProduct(Long id, ProductRequestDTO dto);
}
