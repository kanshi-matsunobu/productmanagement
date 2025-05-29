package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductInventoryDTO;
import com.example.demo.entity.Store;
import com.example.demo.entity.StoreInventoryDTO;
import com.example.demo.repository.ProductOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public Page<Product> searchProducts(
            String name,
            Integer largeCategoryId,
            Integer middleCategoryId,
            Integer smallCategoryId,
            Pageable pageable
    ) {
        return productRepository.searchProducts(name, largeCategoryId, middleCategoryId, smallCategoryId, pageable);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById(Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.orElse(null);
    }
    
    public List<ProductInventoryDTO> fetchProductInventoryBatch() {
        return productRepository.fetchProductInventoryBatch();
    }

    public List<StoreInventoryDTO> fetchStoreInventory() {
        List<Store> stores = storeRepository.findAll();
        List<StoreInventoryDTO> result = new ArrayList<>();

        for (Store store : stores) {
            StoreInventoryDTO dto = new StoreInventoryDTO();
            dto.setStoreName(store.getName());
            dto.setAddress(store.getAddress());

            dto.setProducts(productOrderRepository.getProductInfoByStore(store.getId()));
            dto.setOrders(productOrderRepository.getOrderInfoByStore(store.getId()));

            result.add(dto);
        }

        return result;
    }
}