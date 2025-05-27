package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
        SELECT p FROM Product p
        WHERE (:name IS NULL OR p.name LIKE %:name%)
          AND (:smallCategoryId IS NULL OR p.categorySmall.id = :smallCategoryId)
          AND (:middleCategoryId IS NULL OR p.categorySmall.middleCategory.id = :middleCategoryId)
          AND (:largeCategoryId IS NULL OR p.categorySmall.middleCategory.largeCategory.id = :largeCategoryId)
    """)
    Page<Product> searchProducts(
        @Param("name") String name,
        @Param("largeCategoryId") Integer largeCategoryId,
        @Param("middleCategoryId") Integer middleCategoryId,
        @Param("smallCategoryId") Integer smallCategoryId,
        Pageable pageable
    );
}