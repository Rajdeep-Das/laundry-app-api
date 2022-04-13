package com.azure.laundry.laundry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //private Long CartId;
    private Long ProductId;
    private Long ServiceId;
    private int Quantity;
    private double Price;
    private double TotalPrice;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="cart_id", nullable=true,insertable = true,updatable = true)
    private Cart cart;

}