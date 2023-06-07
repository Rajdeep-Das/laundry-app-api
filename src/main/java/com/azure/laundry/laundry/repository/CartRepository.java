package com.azure.laundry.laundry.repository;

import com.azure.laundry.laundry.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Override
    Optional<Cart> findById(Long cartId);

    @Query(value = "SELECT * FROM cart  WHERE user_id = ?1 AND is_active = ?2", nativeQuery = true)
    public Cart findCartByUserId(Long userId, boolean isActive);
}