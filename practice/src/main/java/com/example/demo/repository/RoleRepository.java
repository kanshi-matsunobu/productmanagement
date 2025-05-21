package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    // ロール名をすべて取得
    @Query("SELECT r.name FROM Role r")
    List<String> findAllRoleNames();
}