package com.ecomemerce.project.repository;

import com.ecomemerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
