package com.azure.laundry.laundry.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToBasketRequest {
    private Long ProductId;
    private Long ServiceId;
    private int Quantity;
}
