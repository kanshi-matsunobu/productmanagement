package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50, message = "姓は50文字以内で入力してください")
    @NotBlank(message = "姓を入力してください")
    @Column(nullable = false)
    private String lastName;

    @Size(max = 50, message = "名は50文字以内で入力してください")
    @NotBlank(message = "名を入力してください")
    @Column(nullable = false)
    private String firstName;

    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください")
    @NotBlank(message = "メールアドレスを入力してください")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(max = 255, message = "パスワードは255文字以内で入力してください", groups = AdminValidationGroups.Create.class)
    @NotBlank(message = "パスワードを入力してください", groups = AdminValidationGroups.Create.class)
    @Column(nullable = false)
    private String password;

    @Size(max = 20, message = "電話番号は20文字以内で入力してください")
    @NotBlank(message = "電話番号を入力してください")
    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @NotNull(message = "店舗を選択してください")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull(message = "権限を選択してください")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @NotNull(message = "役職を選択してください")
    private Position position;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    // ===== Getter & Setter =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}