package com.azure.laundry.laundry.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;

    private String icon;
    private String name;
    private String gender;
    private Integer rank;
    private Double price;

}
