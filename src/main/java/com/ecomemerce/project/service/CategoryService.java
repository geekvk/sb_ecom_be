package com.ecomemerce.project.service;

import com.ecomemerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    void addCategory(Category category);
    List<Category> getCategories();
    String deleteCategory(Long categoryId);
    Category updateCategory(Long categoryId, Category category);
}
