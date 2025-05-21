package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminManageService;

@Controller
@RequestMapping("/admin/manage")
public class AdminManageController {

    @Autowired
    private AdminManageService adminManageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 一覧表示（全員アクセス可能）
    @GetMapping
    public String showAdminList(Model model) {
        model.addAttribute("admins", adminManageService.getAllAdmins());
        return "admin_list";
    }

    // 管理者のみがアクセスできる新規作成フォーム表示
    @PreAuthorize("hasAuthority('管理者')")
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("admin", new Admin());
        model.addAttribute("stores", adminManageService.getAllStores());
        model.addAttribute("roles", adminManageService.getAllRoles());
        model.addAttribute("positions", adminManageService.getAllPositions());
        return "admin_create";
    }

    // 管理者のみがアクセスできる新規登録処理
    @PreAuthorize("hasAuthority('管理者')")
    @PostMapping("/create")
    public String createAdmin(@ModelAttribute Admin admin, Model model, RedirectAttributes redirectAttributes) {
        // メールアドレスの重複チェック
        if (adminManageService.isEmailExists(admin.getEmail())) {
            model.addAttribute("error", "このメールアドレスはすでに登録されています。");
            model.addAttribute("admin", admin);
            model.addAttribute("stores", adminManageService.getAllStores());
            model.addAttribute("roles", adminManageService.getAllRoles());
            model.addAttribute("positions", adminManageService.getAllPositions());
            return "admin_create";
        }

        // 登録処理
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminManageService.saveAdmin(admin);

        // 成功メッセージを一時的に渡す
        redirectAttributes.addFlashAttribute("message", "新規管理者を登録しました！");
        return "redirect:/admin/manage";
    }
}