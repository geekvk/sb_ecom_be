package com.ecomemerce.project.controller;


import com.ecomemerce.project.config.AppConstants;
import com.ecomemerce.project.payload.CategoryDto;
import com.ecomemerce.project.payload.CategoryResponse;
import com.ecomemerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CategoryController {
    private final HttpMessageConverters messageConverters;
    private CategoryService categoryService;

    /*
        http://localhost:8080/api/echo?message="hi"
     */
//    @GetMapping("echo")
//    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", required = false) String message) {
////    public ResponseEntity<String> echoMessage(@RequestParam(name = "message",  defaultValue = "Hello world") String message) {
//        return new ResponseEntity<>("Echoed Message " + message, HttpStatus.OK);
//    }



    @Autowired
    public CategoryController(CategoryService categoryService, HttpMessageConverters messageConverters) {
        this.categoryService = categoryService;
        this.messageConverters = messageConverters;
    }

    @GetMapping("public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORY_ORDER, required = false) String sortOrder
            ) {
        CategoryResponse categoryResponse = categoryService.getCategories(
                pageNumber,
                pageSize,
                sortBy,
                sortOrder
        );
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("admin/category")
    public ResponseEntity<CategoryDto> setCategories(@Valid @RequestBody CategoryDto categoryDto) {
        /*
            @Valid for getting user friendy response. without adding @valid entity valid fild without adding data shows 500 errrr. now its 400 bad request
         */
        CategoryDto savedCategoryDto =  categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }
    @DeleteMapping("admin/category/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long categoryId) {
        CategoryDto deletedCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }

    @PutMapping("admin/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDto categoryDto
    ) {
       CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
       return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }


}
