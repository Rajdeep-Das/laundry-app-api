package com.azure.laundry.laundry.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    Long UserId;
    Date DeliveryDate;
    String DeliveryType;
    int TotalItems;
    double DeliveryPrice;
    Long DriverId;
    String Status;
    String PaymentType;
    Double PaidAmount;
    Double Tax;
    Double SubTotal;
    Double TotalPrice;
    String ExpectedPickUpMin;
    String ExpectedPickUpMax;
    String ExpectedDeliveryMin;
    String ExpectedDeliveryMax;
    Long CartId;
    String DeliveryAddress;
    Date PickupDate;
    String deliverySlot;
    String pickupSlot;
    String LogisticCharge;
    Double discount;
    String paymentResponse;
    String PaymentStatus;
    String note;
    String AdminNote;
    Date paymentDate;
    Date collectedDate;
    Date deliveredDate;
    String laundryInstruction;

}