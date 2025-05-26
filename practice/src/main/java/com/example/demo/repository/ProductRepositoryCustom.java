package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Product;

public interface ProductRepositoryCustom {
    Page<Product> searchProducts(
        String name,
        Integer largeCategoryId,
        Integer middleCategoryId,
        Integer smallCategoryId,
        Pageable pageable
    );
}