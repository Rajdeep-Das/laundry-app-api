package com.azure.laundry.laundry.service;

import com.azure.laundry.laundry.constant.DeliveryType;
import com.azure.laundry.laundry.models.Cart;
import com.azure.laundry.laundry.models.CartItem;
import com.azure.laundry.laundry.repository.CartItemRepository;
import com.azure.laundry.laundry.repository.CartRepository;
import com.azure.laundry.laundry.repository.ProductRepository;
import com.azure.laundry.laundry.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    public Cart createCart(UserDetailsImpl userDetails){
        Cart cart = new Cart();
        cart.setUserId(userDetails.getId());
        cart.setDeliveryType(DeliveryType.STANDARD);
        cart.setTax(0.0);
        cart.setTotalPrice(0.0);
        cart.setTotalItems(0);

        cartRepository.save(cart);

        return cart;
    }







}
