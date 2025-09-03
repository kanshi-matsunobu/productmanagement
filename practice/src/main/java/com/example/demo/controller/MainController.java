package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;

@Controller
public class MainController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/main")
    public String main() {
        Admin loginAdmin = adminService.getLoginAdmin();
        if (loginAdmin != null) {
            adminService.updateLastLoginAt(loginAdmin.getEmail());
        }
        return "main";
    }
}