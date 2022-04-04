package com.azure.laundry.laundry.repository;

import com.azure.laundry.laundry.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}