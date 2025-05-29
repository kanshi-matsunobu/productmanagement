package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ProductInventoryDTO;
import com.example.demo.entity.StoreInventoryDTO;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/batch/inventory")
public class InventoryBatchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getProductInventoryBatch() {
        List<ProductInventoryDTO> productList = productService.fetchProductInventoryBatch();
        Map<String, Object> response = new HashMap<>();
        response.put("products", productList);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/stores-orders")
    @ResponseBody
    public List<StoreInventoryDTO> getStoreInventoryBatch() { // ✅戻り値をListに
        return productService.fetchStoreInventory();
    }
}