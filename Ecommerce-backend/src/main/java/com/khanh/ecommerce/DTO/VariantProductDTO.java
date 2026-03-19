package com.khanh.ecommerce.DTO;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantProductDTO {
	private long productId;
	private String productName;
	private List<VariantItemDTO> variants;
}
