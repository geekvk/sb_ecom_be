package com.ecomemerce.project.service;

import com.ecomemerce.project.model.Product;
import com.ecomemerce.project.payload.ProductDto;
import com.ecomemerce.project.payload.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(Long categoryId, Product product);
    ProductResponse getProducts();
    ProductResponse getProductByProductCategoryId(Long categoryId);
    ProductResponse getProductsByKeyword(String keyword);

}
