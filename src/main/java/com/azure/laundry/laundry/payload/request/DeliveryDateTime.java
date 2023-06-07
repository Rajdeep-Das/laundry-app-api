package com.azure.laundry.laundry.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDateTime {
    private Date deliveryDate;
    private Date pickupDate;
    private String deliverySlot;
    private String pickupSlot;
    private String laundryInstructions;
}
