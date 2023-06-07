package com.azure.laundry.laundry.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // @Column(unique=true)
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

    private boolean isActive;

    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    private Set<CartItem> cartItems;

}