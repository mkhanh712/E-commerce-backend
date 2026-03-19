package com.khanh.ecommerce.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantItemDTO {
	private long id;
	private String sku;
	private String color;
	private String size;
	private double price;
	private int stockQuantity;
}
