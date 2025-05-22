package com.example.demo.service;

import java.util.List;

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
public class AdminManageService {

    private final AdminRepository adminRepository;
    private final StoreRepository storeRepository;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminManageService(
        AdminRepository adminRepository,
        StoreRepository storeRepository,
        RoleRepository roleRepository,
        PositionRepository positionRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.storeRepository = storeRepository;
        this.roleRepository = roleRepository;
        this.positionRepository = positionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 一覧取得
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // IDで1件取得
    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("指定された管理者が見つかりません"));
    }

    // 新規登録
    public void saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }

    // 編集（パスワードは変更しない前提）
    public void updateAdmin(Admin updatedAdmin) {
        Admin admin = getAdminById(updatedAdmin.getId());
        admin.setLastName(updatedAdmin.getLastName());
        admin.setFirstName(updatedAdmin.getFirstName());
        admin.setEmail(updatedAdmin.getEmail());
        admin.setPhoneNumber(updatedAdmin.getPhoneNumber());
        admin.setStore(updatedAdmin.getStore());
        admin.setRole(updatedAdmin.getRole());
        admin.setPosition(updatedAdmin.getPosition());
        adminRepository.save(admin);
    }

    // 削除
    public void deleteAdminById(Integer id) {
        adminRepository.deleteById(id);
    }

    // 店舗・役職・権限 一覧
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    // 重複メールチェック
    public boolean isEmailExists(String email) {
        return adminRepository.findByEmail(email).isPresent();
    }
}