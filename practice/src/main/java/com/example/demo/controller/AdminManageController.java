package com.example.demo.controller;

import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Admin;
import com.example.demo.entity.AdminValidationGroups;
import com.example.demo.service.AdminManageService;
import com.example.demo.service.AdminService;

@Controller
@RequestMapping("/admin/manage")
public class AdminManageController {

    @Autowired
    private AdminManageService adminManageService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AdminService adminService;

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
    public String createAdmin(
            @ModelAttribute @Validated(AdminValidationGroups.Create.class) Admin admin,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("stores", adminManageService.getAllStores());
            model.addAttribute("roles", adminManageService.getAllRoles());
            model.addAttribute("positions", adminManageService.getAllPositions());
            return "admin_create";
        }

        adminManageService.saveAdmin(admin);
        redirectAttributes.addFlashAttribute("message", "管理者を登録しました！");
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
    public String updateAdmin(
            @PathVariable Integer id,
            @ModelAttribute @Validated(AdminValidationGroups.Update.class) Admin admin,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("admin", admin);
            model.addAttribute("stores", adminManageService.getAllStores());
            model.addAttribute("roles", adminManageService.getAllRoles());
            model.addAttribute("positions", adminManageService.getAllPositions());
            return "admin_edit";
        }

        adminManageService.updateAdmin(admin);
        redirectAttributes.addFlashAttribute("message", "管理者情報を更新しました！");
        return "redirect:/admin/manage/" + admin.getId();
    }

    // 管理者のみが実行できる削除処理
    @PreAuthorize("hasAuthority('管理者')")
    @PostMapping("/{id}/delete")
    public String deleteAdmin(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes,
            Principal principal,
            HttpServletRequest request
    ) {
        // 今ログイン中のユーザーを取得（emailから）
        Admin loggedInAdmin = adminService.findByEmail(principal.getName());

        // 削除処理
        adminManageService.deleteAdminById(id);

        // 削除対象が自分自身だった場合 → セッション破棄 & ログイン画面にリダイレクト
        if (loggedInAdmin.getId().equals(id)) {
            request.getSession().invalidate(); // セッション破棄
            return "redirect:/admin/login?selfDeleted=true";
        }

        redirectAttributes.addFlashAttribute("message", "管理者を削除しました！");
        return "redirect:/admin/manage";
    }
}