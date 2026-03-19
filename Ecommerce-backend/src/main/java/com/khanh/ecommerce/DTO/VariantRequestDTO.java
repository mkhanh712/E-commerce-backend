package com.khanh.ecommerce.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantRequestDTO {
	private String sku;
	private String color;
	private String size;
	private double price;
	private int stockQuantity;
	private long productId;
}
