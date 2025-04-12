package com.ecomemerce.project.service;

import com.ecomemerce.project.exception.APIException;
import com.ecomemerce.project.exception.ResourceNotFoundException;
import com.ecomemerce.project.model.Category;
import com.ecomemerce.project.repository.CategoryRepository;
import org.aspectj.weaver.patterns.TypeCategoryTypePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private long nextCategoryId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void addCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category already exists");
        }
       categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);
        Category foundCategory = savedCategory
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(foundCategory);
        return "Category deleted";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);

        Category foundCategory = savedCategory
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        foundCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(foundCategory);
    }


}
