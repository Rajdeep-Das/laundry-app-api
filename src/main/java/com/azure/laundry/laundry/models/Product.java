package com.azure.laundry.laundry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String Icon;
    private String Name;
    private String Gender;
    private Integer Rank;
    // private Long CreatedBy;
    // private Long UpdatedBy;
    // private Boolean IsDeleted;
    // private Boolean IsActive;
    // private Date CreatedOn;
    // private Date UpdatedOn;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    @JsonIgnore
    private Service service;

}