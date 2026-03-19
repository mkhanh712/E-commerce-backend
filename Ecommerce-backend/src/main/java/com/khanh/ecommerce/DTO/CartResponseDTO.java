package com.khanh.ecommerce.DTO;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {
	private Long cartId;
    private String username;
    private double totalPrice;
    private List<CartLineItemDTO> items;
}
