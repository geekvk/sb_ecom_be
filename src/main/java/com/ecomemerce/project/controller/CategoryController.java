package com.ecomemerce.project.controller;


import com.ecomemerce.project.model.Category;
import com.ecomemerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CategoryController {
    private CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("public/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("admin/category")
    public ResponseEntity<String> setCategories(@Valid @RequestBody Category category) {
        /*
            @Valid for getting user friendy response. without adding @valid entity valid fild without adding data shows 500 errrr. now its 400 bad request
         */
        categoryService.addCategory(category);
        return new ResponseEntity<>("added successfully", HttpStatus.CREATED);
    }
    @DeleteMapping("admin/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("admin/category/{categoryId}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody Category category
    ) {
       Category updatedCategory = categoryService.updateCategory(categoryId, category);
       return new ResponseEntity<>(updatedCategory.getCategoryName(), HttpStatus.OK);
    }


}
