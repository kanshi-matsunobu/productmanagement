package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOrder;
import com.example.demo.entity.Store;
import com.example.demo.repository.ProductOrderRepository;

@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public void saveOrder(Product product, int quantity, int adminId, Store store) {
        ProductOrder order = new ProductOrder();

        // 商品IDをセット（ManyToOneのProductは参照専用なので、IDをセット）
        order.setProductId(product.getId());

        // 管理者ID・店舗ID・数量をセット
        order.setAdminId(adminId);
        order.setStoreId(store.getId());
        order.setOrderQuantity(quantity);

        // 合計金額（＝仕入原価 × 数量）
        BigDecimal costPrice = product.getCostPrice();
        if (costPrice == null) {
            costPrice = BigDecimal.ZERO;
        }
        BigDecimal total = costPrice.multiply(BigDecimal.valueOf(quantity));
        order.setTotalPrice(total);

        // 保存
        productOrderRepository.save(order);
    }

    public List<ProductOrder> getOrdersByAdminId(Integer adminId) {
        return productOrderRepository.findByAdminId(adminId);
    }
}