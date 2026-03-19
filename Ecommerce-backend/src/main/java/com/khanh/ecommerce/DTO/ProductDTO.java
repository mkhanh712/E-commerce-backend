package com.khanh.ecommerce.DTO;

import java.time.LocalDateTime;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Long categoryId;
    private String categoryName;
}
