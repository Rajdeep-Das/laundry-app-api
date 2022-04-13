package com.azure.laundry.laundry.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_price")
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long ServiceId;
    private Long ProductId;
    private Double Price;

//    private Long CreatedBy;
//    private Long UpdatedBy;
//    private Boolean IsDeleted;
//    private Boolean IsActive;
//    private Date CreatedOn;
//    private Date UpdatedOn;

}