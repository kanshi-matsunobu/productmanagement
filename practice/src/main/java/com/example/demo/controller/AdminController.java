package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "admin_signup";
    }

    @PostMapping("/signup")
    public String registerAdmin(@ModelAttribute Admin admin, Model model) {
        adminService.saveAdmin(admin);
        model.addAttribute("message", "管理者登録が完了しました！");
        model.addAttribute("stores", adminService.getAllStores());
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("positions", adminService.getAllPositions());
        return "admin_signup"; // 成功時に再度同じ画面を表示
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // 正しいログイン画面名
    }
}