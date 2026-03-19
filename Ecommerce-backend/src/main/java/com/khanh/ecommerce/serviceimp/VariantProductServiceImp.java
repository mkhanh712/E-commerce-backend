package com.khanh.ecommerce.serviceimp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.khanh.ecommerce.DTO.VariantProductDTO;
import com.khanh.ecommerce.DTO.VariantRequestDTO;
import com.khanh.ecommerce.DTO.VariantItemDTO;
import com.khanh.ecommerce.entity.Product;
import com.khanh.ecommerce.entity.VariantProduct;
import com.khanh.ecommerce.repository.ProductRepository;
import com.khanh.ecommerce.repository.VariantProductRepository;
import com.khanh.ecommerce.service.VariantProductService;

@Service
public class VariantProductServiceImp implements VariantProductService {
	@Autowired
	private VariantProductRepository variantProductRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public VariantProduct createVProduct(VariantRequestDTO dto) {
		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Product not found")); //ResponseStatusException(status, reason)
		VariantProduct vproduct = new VariantProduct();
		vproduct.setSku(dto.getSku());
		vproduct.setColor(dto.getColor());
		vproduct.setSize(dto.getSize());
		vproduct.setPrice(dto.getPrice());
		vproduct.setStockQuantity(dto.getStockQuantity());
		vproduct.setProduct(product);
		return variantProductRepository.save(vproduct);
	}
	
	@Override
	public VariantProduct updateVProduct(Long id, VariantRequestDTO dto) {
		VariantProduct vproduct = variantProductRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Variant Product not found"));
		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Product not found" ));
		vproduct.setSku(dto.getSku());
		vproduct.setColor(dto.getColor());
		vproduct.setSize(dto.getSize());
		vproduct.setPrice(dto.getPrice());
		vproduct.setStockQuantity(dto.getStockQuantity());
		vproduct.setProduct(product);
		return variantProductRepository.save(vproduct);
	}
	
	//VariantItemDTO ko chứa product, lấy các dữ liệu fields từ variants(List<variantproduct>)
	// và map vào variantsDTO nên khi return sẽ ko trả ra product nữa
	@Override
	public VariantProductDTO getByProductId(Long productId){
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Product not found"));
		List<VariantProduct> variants = variantProductRepository.findByProductId(productId);
		List<VariantItemDTO> variantsDTO = variants.stream()
				.map(v -> new VariantItemDTO(
						v.getId(),
						v.getSku(),
		                v.getColor(),
		                v.getSize(),
		                v.getPrice(),
		                v.getStockQuantity()
			))
				.toList();						
		return new VariantProductDTO(
				product.getId(),
				product.getName(),
				variantsDTO);	
	}
}
