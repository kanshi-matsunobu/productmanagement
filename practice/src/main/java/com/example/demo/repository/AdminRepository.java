package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByEmail(String email);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE admins SET last_login_at = :time WHERE email = :email", nativeQuery = true)
    void updateLastLoginAtNative(@Param("email") String email, @Param("time") LocalDateTime time);
    
    @Query("SELECT a FROM Admin a WHERE a.lastLoginAt IS NULL OR a.lastLoginAt < :threshold")
    List<Admin> findInactiveAdmins(@Param("threshold") LocalDateTime threshold);
}