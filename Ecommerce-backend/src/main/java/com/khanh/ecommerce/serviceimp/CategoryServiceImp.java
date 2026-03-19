package com.khanh.ecommerce.serviceimp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khanh.ecommerce.DTO.CategoryDTO;
import com.khanh.ecommerce.entity.Category;
import com.khanh.ecommerce.repository.CategoryRepository;
import com.khanh.ecommerce.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService{
	@Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(p -> new CategoryDTO( //List phải dùng stream().map còn Page thì .map
        		p.getId(),
        		p.getName(),
        		p.getDescription()
        ))
        .toList();
    }
}
