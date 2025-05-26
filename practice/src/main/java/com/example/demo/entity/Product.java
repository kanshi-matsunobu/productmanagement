package com.example.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String imageUrl;

    private BigDecimal costPrice;

    private BigDecimal retailPrice;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "category_small_id")
    private CategorySmall categorySmall;

    // ===== Getter & Setter =====

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public BigDecimal getCostPrice() { return costPrice; }

    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }

    public BigDecimal getRetailPrice() { return retailPrice; }

    public void setRetailPrice(BigDecimal retailPrice) { this.retailPrice = retailPrice; }

    public Manufacturer getManufacturer() { return manufacturer; }

    public void setManufacturer(Manufacturer manufacturer) { this.manufacturer = manufacturer; }

    public CategorySmall getCategorySmall() { return categorySmall; }

    public void setCategorySmall(CategorySmall categorySmall) { this.categorySmall = categorySmall; }
}