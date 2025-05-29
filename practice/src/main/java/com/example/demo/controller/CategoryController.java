package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.CategoryMiddle;
import com.example.demo.entity.CategorySmall;
import com.example.demo.repository.CategoryMiddleRepository;
import com.example.demo.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CategoryMiddleRepository categoryMiddleRepository;

    @GetMapping("/large")
    public String showLargeCategories(Model model) {
        model.addAttribute("largeCategories", categoryService.getAllLargeCategories());
        return "categories_large_list";  // HTMLのテンプレート名
    }
    
    @GetMapping("/large/{largeId}/middle")
    public String showMiddleCategories(@PathVariable("largeId") int largeId, Model model) {
        List<CategoryMiddle> middleList = categoryService.getMiddleCategoriesByLargeId(largeId);
        model.addAttribute("middleCategories", middleList);
        return "categories_middle_list";
    }
    
    @GetMapping("/middle/{id}/small")
    public String showSmallCategories(@PathVariable("id") int middleId, Model model) {
        List<CategorySmall> smallCategories = categoryService.getSmallCategoriesByMiddleId(middleId);
        model.addAttribute("smallCategories", smallCategories);

        // 必要な情報を渡す（戻る先のURL用）
        CategoryMiddle middle = categoryMiddleRepository.findById(middleId).orElseThrow();
        model.addAttribute("largeId", middle.getLargeCategory().getId());

        return "categories_small_list";
    }
}