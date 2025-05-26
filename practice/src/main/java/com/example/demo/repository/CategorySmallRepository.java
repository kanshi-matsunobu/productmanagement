package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CategorySmall;

public interface CategorySmallRepository extends JpaRepository<CategorySmall, Integer> {
}