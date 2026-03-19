package com.khanh.ecommerce.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartLineItemDTO {
	private Long id;
    private String variantName;
    private String color;
    private String size;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
}
