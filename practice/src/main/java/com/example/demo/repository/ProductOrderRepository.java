package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.OrderInfoDTO;
import com.example.demo.entity.ProductInfoDTO;
import com.example.demo.entity.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
	List<ProductOrder> findByAdminId(Integer adminId);
	
	@Query("""
		    SELECT new com.example.demo.entity.ProductInfoDTO(
		        po.product.name,
		        po.product.retailPrice,
		        SUM(po.orderQuantity)
		    )
		    FROM ProductOrder po
		    WHERE po.store.id = :storeId
		    GROUP BY po.product.id
		""")
		List<ProductInfoDTO> getProductInfoByStore(@Param("storeId") Integer storeId);

		@Query("""
		    SELECT new com.example.demo.entity.OrderInfoDTO(
		        po.product.name,
		        CONCAT(po.admin.lastName, ' ', po.admin.firstName),
		        po.orderQuantity,
		        po.totalPrice
		    )
		    FROM ProductOrder po
		    WHERE po.store.id = :storeId
		""")
		List<OrderInfoDTO> getOrderInfoByStore(@Param("storeId") Integer storeId);
}
