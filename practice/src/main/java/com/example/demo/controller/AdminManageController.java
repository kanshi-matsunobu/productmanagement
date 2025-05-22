package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 詳細表示（全員アクセス可能）
    @GetMapping("/{id}")
    public String showAdminDetail(@PathVariable Integer id, Model model) {
        Admin admin = adminManageService.getAdminById(id);
        model.addAttribute("admin", admin);
        return "admin_detail";
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
        if (adminManageService.isEmailExists(admin.getEmail())) {
            model.addAttribute("error", "このメールアドレスはすでに登録されています。");
            model.addAttribute("admin", admin);
            model.addAttribute("stores", adminManageService.getAllStores());
            model.addAttribute("roles", adminManageService.getAllRoles());
            model.addAttribute("positions", adminManageService.getAllPositions());
            return "admin_create";
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminManageService.saveAdmin(admin);
        redirectAttributes.addFlashAttribute("message", "新規管理者を登録しました！");
        return "redirect:/admin/manage";
    }

    // 管理者のみがアクセスできる編集フォーム表示
    @PreAuthorize("hasAuthority('管理者')")
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Admin admin = adminManageService.getAdminById(id);
        model.addAttribute("admin", admin);
        model.addAttribute("stores", adminManageService.getAllStores());
        model.addAttribute("roles", adminManageService.getAllRoles());
        model.addAttribute("positions", adminManageService.getAllPositions());
        return "admin_edit";
    }

    // 管理者のみがアクセスできる編集処理
    @PreAuthorize("hasAuthority('管理者')")
    @PostMapping("/{id}/edit")
    public String updateAdmin(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {
        adminManageService.updateAdmin(admin);
        redirectAttributes.addFlashAttribute("message", "管理者情報を更新しました！");
        return "redirect:/admin/manage/" + admin.getId();
    }

    // 管理者のみが実行できる削除処理
    @PreAuthorize("hasAuthority('管理者')")
    @PostMapping("/{id}/delete")
    public String deleteAdmin(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        adminManageService.deleteAdminById(id);
        redirectAttributes.addFlashAttribute("message", "管理者を削除しました！");
        return "redirect:/admin/manage";
    }
}