package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CategoryMiddle;

public interface CategoryMiddleRepository extends JpaRepository<CategoryMiddle, Integer> {
	List<CategoryMiddle> findByLargeCategory_Id(int largeId);
}