package com.khanh.ecommerce.service;

import com.khanh.ecommerce.DTO.VariantProductDTO;
import com.khanh.ecommerce.DTO.VariantRequestDTO;
import com.khanh.ecommerce.entity.VariantProduct;

public interface VariantProductService {
	VariantProduct createVProduct(VariantRequestDTO dto);
	VariantProduct updateVProduct(Long id, VariantRequestDTO dto);
	VariantProductDTO getByProductId(Long productId);
}
