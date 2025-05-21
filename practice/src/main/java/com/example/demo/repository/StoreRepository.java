package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    // 店舗名をすべて取得
    @Query("SELECT s.name FROM Store s")
    List<String> findAllStoreNames();
}