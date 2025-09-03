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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("admin", new Admin());
        model.addAttribute("stores", adminService.getAllStores());
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("positions", adminService.getAllPositions());
        return "admin_signup"; // 画面ファイル名
    }

    @PostMapping("/signup")
    public String registerAdmin(
            @ModelAttribute @Valid Admin admin,
            BindingResult bindingResult,
            Model model) {

        // バリデーションエラーがある場合、フォームに戻す
        if (bindingResult.hasErrors()) {
            model.addAttribute("stores", adminService.getAllStores());
            model.addAttribute("roles", adminService.getAllRoles());
            model.addAttribute("positions", adminService.getAllPositions());
            return "admin_signup";
        }

        try {
            adminService.saveAdmin(admin);
            model.addAttribute("message", "管理者登録が完了しました！");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("stores", adminService.getAllStores());
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("positions", adminService.getAllPositions());
        return "admin_signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}