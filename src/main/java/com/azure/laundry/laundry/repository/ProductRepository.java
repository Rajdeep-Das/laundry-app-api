package com.azure.laundry.laundry.repository;

import com.azure.laundry.laundry.models.Product;
import com.azure.laundry.laundry.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM Product  WHERE service_id = ?1",nativeQuery = true)
    public List<Product> findAllProductByServiceId(Long ServiceId);
}