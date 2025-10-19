package com.ecomemerce.project.repository;

import com.ecomemerce.project.model.Category;
import com.ecomemerce.project.model.Product;
import com.ecomemerce.project.payload.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getProductByCategoryOrderByPriceAsc(Category category);
    List<Product> findByProductNameIsLikeIgnoreCase(String productName);
}
