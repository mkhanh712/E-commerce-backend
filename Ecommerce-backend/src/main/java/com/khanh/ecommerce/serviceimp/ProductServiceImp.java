package com.khanh.ecommerce.serviceimp;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.khanh.ecommerce.DTO.ProductDTO;
import com.khanh.ecommerce.DTO.ProductRequestDTO;
import com.khanh.ecommerce.entity.Category;
import com.khanh.ecommerce.entity.Product;
import com.khanh.ecommerce.repository.CategoryRepository;
import com.khanh.ecommerce.repository.ProductRepository;
import com.khanh.ecommerce.service.ProductService;

@Service
public class ProductServiceImp implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Page<ProductDTO> getProductsByCategory(Long categoryId, Pageable pageable){
		Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
		return products.map(p -> new ProductDTO(
				p.getId(),
				p.getName(),
				p.getDescription(),
				p.getCreatedAt(),
				p.getCategory().getId(),
				p.getCategory().getName()
		));
	}
	
	@Override
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
			        HttpStatus.NOT_FOUND, "Product not found with id: " + id
			    ));
		return new ProductDTO(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getCreatedAt(),
				product.getCategory().getId(),
				product.getCategory().getName()
		);
	}
	
	@Override
	public Product createProduct(ProductRequestDTO dto) {
		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Category not found "));
		Product product = new Product();
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setCategory(category);
		return productRepository.save(product);
	}
	
	@Override
	public Product updateProduct(Long id, ProductRequestDTO dto) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Product not found" ));
		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Category not found "));
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setCategory(category);
		return productRepository.save(product);			
	}
}
