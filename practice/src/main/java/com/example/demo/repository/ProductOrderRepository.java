package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
	List<ProductOrder> findByAdminId(Integer adminId);
}
