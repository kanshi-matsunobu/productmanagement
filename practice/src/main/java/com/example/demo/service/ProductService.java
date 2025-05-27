package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(
            String name,
            Integer largeCategoryId,
            Integer middleCategoryId,
            Integer smallCategoryId,
            Pageable pageable
    ) {
    
    	return productRepository.searchProducts(name, largeCategoryId, middleCategoryId, smallCategoryId, pageable);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    public Product findById(Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.orElse(null); // 見つからなければ null を返す（今後例外投げるようにしてもOK）
    }
}