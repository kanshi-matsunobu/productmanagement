package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    // ポジション名をすべて取得
    @Query("SELECT p.name FROM Position p")
    List<String> findAllPositionNames();
}