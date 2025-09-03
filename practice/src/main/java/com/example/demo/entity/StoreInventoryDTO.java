package com.example.demo.entity;

import java.util.List;

public class StoreInventoryDTO {
    private String storeName;
    private String address;
    private List<ProductInfoDTO> products;
    private List<OrderInfoDTO> orders;

    // ✅ デフォルトコンストラクタ
    public StoreInventoryDTO() {
    }

    // ✅ 全項目のコンストラクタ（必要に応じて）
    public StoreInventoryDTO(String storeName, String address, List<ProductInfoDTO> products, List<OrderInfoDTO> orders) {
        this.storeName = storeName;
        this.address = address;
        this.products = products;
        this.orders = orders;
    }

    // ===== Getter & Setter =====

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductInfoDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoDTO> products) {
        this.products = products;
    }

    public List<OrderInfoDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfoDTO> orders) {
        this.orders = orders;
    }
}