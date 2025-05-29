package com.example.demo.entity;

import java.math.BigDecimal;

public class ProductInventoryDTO {
    private String largeCategoryName;
    private String middleCategoryName;
    private String smallCategoryName;
    private String manufacturerName;
    private String productName;
    private String description;
    private BigDecimal costPrice;             
    private BigDecimal retailPrice; 

    public String getLargeCategoryName() {
        return largeCategoryName;
    }
    public void setLargeCategoryName(String largeCategoryName) {
        this.largeCategoryName = largeCategoryName;
    }
    public String getMiddleCategoryName() {
        return middleCategoryName;
    }
    public void setMiddleCategoryName(String middleCategoryName) {
        this.middleCategoryName = middleCategoryName;
    }
    public String getSmallCategoryName() {
        return smallCategoryName;
    }
    public void setSmallCategoryName(String smallCategoryName) {
        this.smallCategoryName = smallCategoryName;
    }
    public String getManufacturerName() {
        return manufacturerName;
    }
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getCostPrice() {
        return costPrice;
    }
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getretailPrice() {
        return retailPrice;
    }
    public void setretailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }
    public ProductInventoryDTO(
    	    String largeCategoryName,
    	    String middleCategoryName,
    	    String smallCategoryName,
    	    String manufacturerName,
    	    String productName,
    	    String description,
    	    BigDecimal costPrice,
    	    BigDecimal retailPrice
    	) {
    	    this.largeCategoryName = largeCategoryName;
    	    this.middleCategoryName = middleCategoryName;
    	    this.smallCategoryName = smallCategoryName;
    	    this.manufacturerName = manufacturerName;
    	    this.productName = productName;
    	    this.description = description;
    	    this.costPrice = costPrice;
    	    this.retailPrice = retailPrice;
    	}
}