package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CategoryLarge;

public interface CategoryLargeRepository extends JpaRepository<CategoryLarge, Integer> {
}