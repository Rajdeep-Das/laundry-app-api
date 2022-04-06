package com.azure.laundry.laundry.repository;

import com.azure.laundry.laundry.models.Product;
import com.azure.laundry.laundry.models.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    @Query(value = "SELECT * FROM product_price  WHERE service_id = ?1 AND product_id = ?2",nativeQuery = true)
    public ProductPrice findAllProductPriceByServiceIdAndProductId(Long ServiceId,Long ProductId);
}