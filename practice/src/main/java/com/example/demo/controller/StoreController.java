package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/detail")
    public String showStoreDetail(Model model) {
        // 店舗情報を取得してモデルに追加
        model.addAttribute("stores", storeService.getAllStores());
        return "store_detail";
    }
    
    @GetMapping("/edit/{id}")
    public String editStore(@PathVariable("id") Long id, Model model) {
        model.addAttribute("store", storeService.getStoreById(id));
        return "store_edit";
        }
    
    @PostMapping("/edit")
    public String updateStore(@ModelAttribute Store store) {
        storeService.updateStore(store);  // Serviceに保存処理を任せる
        return "redirect:/store/detail"; // 編集後は一覧にリダイレクト
    }
}