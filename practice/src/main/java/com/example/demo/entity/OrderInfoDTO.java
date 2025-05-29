package com.example.demo.entity;

import java.math.BigDecimal;

public class OrderInfoDTO {
    private String productName;
    private String adminName;
    private int orderQuantity;
    private BigDecimal totalPrice;

    public OrderInfoDTO() {
    }

    public OrderInfoDTO(String productName, String adminName, int orderQuantity, BigDecimal totalPrice) {
        this.productName = productName;
        this.adminName = adminName;
        this.orderQuantity = orderQuantity;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}