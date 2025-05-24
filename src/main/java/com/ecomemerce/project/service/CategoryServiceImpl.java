package com.ecomemerce.project.service;

import com.ecomemerce.project.exception.APIException;
import com.ecomemerce.project.exception.ResourceNotFoundException;
import com.ecomemerce.project.model.Category;
import com.ecomemerce.project.payload.CategoryDto;
import com.ecomemerce.project.payload.CategoryResponse;
import com.ecomemerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private long nextCategoryId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb != null) {
            throw new APIException("Category already exists");
        }
        Category result = categoryRepository.save(category);
        return modelMapper.map(result, CategoryDto.class);
    }

    @Override
    public CategoryResponse getCategories(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    ) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        /*
            pageable -> interface that represents the reuqest from specific page of the data to  represnt the result
         */
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories= categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        List<CategoryDto> categoryDtos = categories.stream().map(
                category -> modelMapper.map(category, CategoryDto.class)).
                collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContents(categoryDtos);
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        return categoryResponse;
    }

    @Override
    public CategoryDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {

        Category foundCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Category updatedCategory = modelMapper.map(categoryDto, Category.class);
        updatedCategory.setCategoryId(foundCategory.getCategoryId());
        updatedCategory.setCategoryName(categoryDto.getCategoryName());
        foundCategory = categoryRepository.save(updatedCategory);
        return modelMapper.map(foundCategory, CategoryDto.class);
    }


}
