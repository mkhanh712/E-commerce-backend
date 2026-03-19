package com.khanh.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "variant_products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class VariantProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String sku;
	private String color;
	private String size;
	private double price;
	private int stockQuantity;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
