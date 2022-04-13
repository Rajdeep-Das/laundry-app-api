package com.azure.laundry.laundry.controllers;

import com.azure.laundry.laundry.CommonResponse;
import com.azure.laundry.laundry.models.Cart;
import com.azure.laundry.laundry.models.CartItem;
import com.azure.laundry.laundry.models.ProductPrice;
import com.azure.laundry.laundry.payload.request.AddToBasketRequest;
import com.azure.laundry.laundry.repository.CartItemRepository;
import com.azure.laundry.laundry.repository.CartRepository;
import com.azure.laundry.laundry.security.services.UserDetailsImpl;
import com.azure.laundry.laundry.service.CartService;
import com.azure.laundry.laundry.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/cart")
@SecurityRequirement(name = "jwtAuth")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartRepository cartRepository;

    @GetMapping("/")
    public ResponseEntity<?> getUserCart(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Cart userCart = cartRepository.findCartByUserId(userDetails.getId());

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatusCode(HttpStatus.OK.value());
        commonResponse.setMessage("success");
        commonResponse.setData(userCart);

        return  new ResponseEntity<>(commonResponse,HttpStatus.OK);
    }



    @PostMapping("/addToBasket")
    public ResponseEntity<?> addToBasket(@RequestBody AddToBasketRequest addToBasketRequest[]){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();


        // if user cart exists delete cart and add new
        Cart userCart = cartRepository.findCartByUserId(userDetails.getId());
        if(userCart!=null){
         cartRepository.delete(userCart);
        }

        Cart cart = cartService.createCart(userDetails);
        Set<CartItem> cartItems = new HashSet<>();

        for(AddToBasketRequest basket : addToBasketRequest){

            CartItem cartItem = new CartItem();

            ProductPrice productPrice = productService
                    .findProductPriceByServiceIdAndProductId(basket.getServiceId(), basket.getProductId());

            cartItem.setProductId(basket.getProductId());
            cartItem.setServiceId(basket.getServiceId());
            cartItem.setQuantity(basket.getQuantity());
            cartItem.setPrice(productPrice.getPrice());
            Double totalPrice = productPrice.getPrice() * basket.getQuantity();
            cartItem.setTotalPrice(totalPrice);
            cartItem.setCart(cart);

            cartItemRepository.save(cartItem);
            cartItems.add(cartItem);
        }
        cart.setCartItems(cartItems);
        cart.setTotalItems(cartItems.size());
        // Calculate Total Cart Price
        Double totalCartPrice = 0.0;
        for (CartItem cartItem:cartItems){
            if(cartItem!=null){
                totalCartPrice = totalCartPrice + cartItem.getTotalPrice();
            }
        }
        // here total & subtotal price is same
        cart.setSubTotal(totalCartPrice);
        cart.setTotalPrice(totalCartPrice);

        cartRepository.save(cart);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatusCode(HttpStatus.OK.value());
        commonResponse.setMessage("success");
        commonResponse.setData(cart);

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}
