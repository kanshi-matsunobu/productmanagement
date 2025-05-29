package com.example.demo.entity;

import java.math.BigDecimal;

public class ProductInfoDTO {

    private String productName;
    private BigDecimal retailPrice;
    private Long totalOrderQuantity;

    // コンストラクタ（JPAが使う）
    public ProductInfoDTO(String productName, BigDecimal retailPrice, Long totalOrderQuantity) {
        this.productName = productName;
        this.retailPrice = retailPrice;
        this.totalOrderQuantity = totalOrderQuantity;
    }

    // ゲッター・セッター
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getTotalOrderQuantity() {
        return totalOrderQuantity;
    }

    public void setTotalOrderQuantity(Long totalOrderQuantity) {
        this.totalOrderQuantity = totalOrderQuantity;
    }
}