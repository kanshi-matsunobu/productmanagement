package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    // 管理者を保存（パスワードをハッシュ化して保存）
    public Admin saveAdmin(Admin admin) {
        // パスワードをハッシュ化
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    // 役職一覧を取得
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // ポジション一覧を取得
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    // 店舗一覧を取得
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}