package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CategoryLarge;
import com.example.demo.entity.CategoryMiddle;
import com.example.demo.entity.CategorySmall;

public interface CategoryService {
    List<CategoryLarge> getAllLargeCategories();
    List<CategoryMiddle> getAllMiddleCategories();
    List<CategorySmall> getAllSmallCategories();
}
