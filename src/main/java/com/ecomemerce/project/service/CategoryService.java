package com.ecomemerce.project.service;

import com.ecomemerce.project.model.Category;
import com.ecomemerce.project.payload.CategoryDto;
import com.ecomemerce.project.payload.CategoryResponse;


public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDto deleteCategory(Long categoryId);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
}
