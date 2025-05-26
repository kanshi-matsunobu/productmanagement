package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOrder;
import com.example.demo.repository.ProductOrderRepository;

@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public void saveOrder(Product product, int quantity, int adminId, int storeId) {
        ProductOrder order = new ProductOrder();
        order.setProductId(product.getId());
        order.setAdminId(adminId);
        order.setStoreId(storeId);
        order.setOrderQuantity(quantity);

        BigDecimal costPrice = product.getCostPrice();
        if (costPrice == null) {
            costPrice = BigDecimal.ZERO;
        }
        BigDecimal total = costPrice.multiply(BigDecimal.valueOf(quantity));

        order.setTotalPrice(total);
        productOrderRepository.save(order);
    }
    
    public List<ProductOrder> getOrdersByAdminId(Integer adminId) {
        return productOrderRepository.findByAdminId(adminId);
    }
}