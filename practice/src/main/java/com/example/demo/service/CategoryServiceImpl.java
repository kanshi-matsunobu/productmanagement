package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CategoryLarge;
import com.example.demo.entity.CategoryMiddle;
import com.example.demo.entity.CategorySmall;
import com.example.demo.repository.CategoryLargeRepository;
import com.example.demo.repository.CategoryMiddleRepository;
import com.example.demo.repository.CategorySmallRepository;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryLargeRepository categoryLargeRepository;

    @Autowired
    private CategoryMiddleRepository categoryMiddleRepository;

    @Autowired
    private CategorySmallRepository categorySmallRepository;

    @Override
    public List<CategoryLarge> getAllLargeCategories() {
        return categoryLargeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<CategoryMiddle> getAllMiddleCategories() {
        return categoryMiddleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<CategorySmall> getAllSmallCategories() {
        return categorySmallRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
    
    @Override
    public List<CategorySmall> getSmallCategoriesByMiddleId(int middleId) {
        return categorySmallRepository.findByMiddleCategory_Id(middleId);
    }
    
    @Override
    public List<CategoryMiddle> getMiddleCategoriesByLargeId(int largeId) {
        return categoryMiddleRepository.findByLargeCategory_Id(largeId);
    }
}