package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showEditForm(@PathVariable Integer id, Model model) {
        Store store = storeService.getStoreById(id); 
        model.addAttribute("store", store);
        return "store_edit";
    }
    
    @PostMapping("/edit")
    public String updateStore(@ModelAttribute Store store, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (store.getName().length() > 255) {
            result.rejectValue("name", "length", "店舗名は255文字以内で入力してください。");
        }
        if (store.getAddress() != null && store.getAddress().length() > 255) {
            result.rejectValue("address", "length", "住所は255文字以内で入力してください。");
        }

        // どちらかのエラーがある場合、編集画面に戻る
        if (result.hasErrors()) {
            model.addAttribute("store", store);
            return "store_edit";
        }

        storeService.updateStore(store);
        redirectAttributes.addFlashAttribute("message", "店舗情報を更新しました");
        return "redirect:/store/detail";
    }
}