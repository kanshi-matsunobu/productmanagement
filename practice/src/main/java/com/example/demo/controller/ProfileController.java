package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/detail")
    public String showProfile(Model model) {
        // ログイン中のユーザー情報を取得
        Admin loggedInAdmin = adminService.getLoggedInAdmin();
        model.addAttribute("admin", loggedInAdmin);
        return "profile";  // profile.htmlを表示
    }
    
    @GetMapping("/edit")
    public String showEditForm(Model model) {
        // ログイン中のユーザー情報を取得
        Admin loggedInAdmin = adminService.getLoggedInAdmin();
        model.addAttribute("admin", loggedInAdmin);
        
     // 店舗、権限、役職の一覧をModelに追加
        model.addAttribute("stores", adminService.getAllStores());
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("positions", adminService.getAllPositions());

        return "profile_edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @ModelAttribute @Valid Admin admin,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、再度フォーム表示に戻す

            // フォームに必要な選択肢をもう一度渡す（← これ大事！）
        	model.addAttribute("admin", admin);
            model.addAttribute("stores", adminService.getAllStores());
            model.addAttribute("roles", adminService.getAllRoles());
            model.addAttribute("positions", adminService.getAllPositions());

            return "profile_edit";
        }

        adminService.updateAdmin(admin);
        return "redirect:/profile/detail";
    }
}
