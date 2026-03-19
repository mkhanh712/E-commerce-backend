package com.khanh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.khanh.ecommerce.DTO.VariantProductDTO;
import com.khanh.ecommerce.DTO.VariantRequestDTO;
import com.khanh.ecommerce.entity.VariantProduct;
import com.khanh.ecommerce.service.VariantProductService;

@RestController
@RequestMapping("/api")
public class VariantProductController {
	@Autowired
	private VariantProductService variantProductService;
	
	@PostMapping("/admin/createVProduct")
	/**
	{
	  "sku": "xxx",
	  "color": "xxx",
	  "size": "x",
	  "price": xxx,
	  "stockQuantity": x,
	  "productId": x
	}
	 */
	public VariantProduct createVProduct(@RequestBody VariantRequestDTO dto) {
		return variantProductService.createVProduct(dto);
	}
	
	@PutMapping("/admin/updateVProduct/vproductid={id}")
	/**
	{
	  "sku": "xxx",
	  "color": "xxx",
	  "size": "x",
	  "price": xxx,
	  "stockQuantity": x,
	  "productId": x
	}
	 */
	public VariantProduct updateVProduct(@PathVariable long id, @RequestBody VariantRequestDTO dto) {
		return variantProductService.updateVProduct(id, dto);
	}
	
	@GetMapping("/public/getByProductId={productId}")
	public VariantProductDTO getByProductId(@PathVariable Long productId) {
		return variantProductService.getByProductId(productId);
	}
}
