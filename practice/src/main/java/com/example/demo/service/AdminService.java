package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Position;
import com.example.demo.entity.Role;
import com.example.demo.entity.Store;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ログイン中の管理者を取得
    public Admin getLoggedInAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User user) {
            String email = user.getUsername();
            return adminRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    // 管理者を新規保存（パスワードはハッシュ化）
    public Admin saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    // 管理者情報の更新
    public Admin updateAdmin(Admin updatedAdmin) {
        Admin admin = adminRepository.findById(updatedAdmin.getId())
            .orElseThrow(() -> new RuntimeException("管理者が見つかりません"));
        admin.setLastName(updatedAdmin.getLastName());
        admin.setFirstName(updatedAdmin.getFirstName());
        admin.setEmail(updatedAdmin.getEmail());
        admin.setPhoneNumber(updatedAdmin.getPhoneNumber());
        return adminRepository.save(admin);
    }

    // メールアドレスの重複確認
    public boolean isEmailExists(String email) {
        return adminRepository.findByEmail(email).isPresent();
    }

    // 一覧取得系
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // 管理者一覧取得
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}