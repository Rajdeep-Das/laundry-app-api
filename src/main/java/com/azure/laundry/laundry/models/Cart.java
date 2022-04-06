package com.azure.laundry.laundry.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;
    private String status;
    private Double tax;
    private Double subTotal;
    private Double totalPrice;
    private int totalItems;
    private String deliveryType;
    private Date deliveryDate;
    private Date pickupDate;
    private String deliverySlot;
    private String pickupSlot;
    private String laundryInstructions;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL)
//    private List<CartItem> cartItems = new ArrayList<>();


}