package com.ecomemerce.project.service;

import com.ecomemerce.project.exception.ResourceNotFoundException;
import com.ecomemerce.project.model.Category;
import com.ecomemerce.project.model.Product;
import com.ecomemerce.project.payload.ProductDto;
import com.ecomemerce.project.payload.ProductResponse;
import com.ecomemerce.project.repository.CategoryRepository;
import com.ecomemerce.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
       product.setCategory(category);
       product.setProductImageUrl("");
       double specialPrice =  product.getPrice() - ( (product.getDiscount() * 0.01) * product.getPrice()); // 100 - (25/100) * 100
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductResponse getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products
                .stream().
                map(product ->
                        modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }

    @Override
    public ProductResponse getProductByProductCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.getProductByCategoryOrderByPriceAsc(category);
        List<ProductDto> productDtos = products.stream()
                .map(product ->
                        modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;

    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameIsLikeIgnoreCase(keyword);
        List<ProductDto> productDtos = products.stream()
                .map(product ->
                        modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }
}
