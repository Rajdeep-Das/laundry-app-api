package com.azure.laundry.laundry.repository;

import com.azure.laundry.laundry.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


}