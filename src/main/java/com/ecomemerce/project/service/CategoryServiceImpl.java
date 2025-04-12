package com.ecomemerce.project.service;

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

    //private List<Category> categories = new ArrayList<>();
    private long nextCategoryId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void addCategory(Category category) {
//        category.setCategoryId(nextCategoryId++);
       categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String deleteCategory(Long categoryId) {

//        categoryRepository.deleteById(categoryId);
//        List<Category> categories = categoryRepository.findAll();
//        Category  category = categories.stream()
//                .filter(c-> c.getCategoryId() == categoryId)
//                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + categoryId + " not found"));
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);

        Category foundCategory = savedCategory
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        categoryRepository.delete(foundCategory);
        return "Category deleted";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
//        List<Category> categories = categoryRepository.findAll();
//        Category  foundCategory = categories.stream()
//                .filter(c-> c.getCategoryId() == categoryId)
//                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + categoryId + " not found"));
//        foundCategory.setCategoryName(category.getCategoryName());
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);

        Category foundCategory = savedCategory
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        foundCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(foundCategory);
    }


}
