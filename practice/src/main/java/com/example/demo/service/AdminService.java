package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    
    public Admin getLoggedInAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            String email = user.getUsername();
            Optional<Admin> adminOptional = adminRepository.findByEmail(email);
            return adminOptional.orElse(null);  // 修正ポイント
        }
        return null;
    }
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

 // ログイン時に呼び出されるユーザー詳細情報の取得
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // OptionalからAdminを取り出す
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // UserDetailsの実装クラス（通常はUserオブジェクトなど）を返す
        return new org.springframework.security.core.userdetails.User(
                admin.getEmail(), 
                admin.getPassword(), 
                new ArrayList<>()
        );
        }
    public Admin updateAdmin(Admin updatedAdmin) {
        Admin admin = adminRepository.findById(updatedAdmin.getId()).orElseThrow(() -> new RuntimeException("管理者が見つかりません"));
        admin.setLastName(updatedAdmin.getLastName());
        admin.setFirstName(updatedAdmin.getFirstName());
        admin.setEmail(updatedAdmin.getEmail());
        admin.setPhoneNumber(updatedAdmin.getPhoneNumber());
        return adminRepository.save(admin);
    }
}