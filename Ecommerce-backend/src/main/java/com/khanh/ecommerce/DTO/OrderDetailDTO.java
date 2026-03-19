package com.khanh.ecommerce.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private Long id;
    private String productName;
    private String color;
    private String size;
    private String sku;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
}
