package com.azure.laundry.laundry.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    private Long CreatedBy;
    private Long UpdatedBy;
    private Boolean IsDeleted;
    private Boolean IsActive;
    private Date CreatedOn;
    private Date UpdatedOn;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    @JsonIgnoreProperties("serviceList")
    private Service service;

//    public Product() {
//    }
//
//    public String getIcon() {
//        return Icon;
//    }
//
//    public void setIcon(String icon) {
//        Icon = icon;
//    }
//
//    public Service getService() {
//        return service;
//    }
//
//    public void setService(Service service) {
//        this.service = service;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public String getGender() {
//        return Gender;
//    }
//
//    public void setGender(String gender) {
//        Gender = gender;
//    }
//
//    public Integer getRank() {
//        return Rank;
//    }
//
//    public void setRank(Integer rank) {
//        Rank = rank;
//    }
//
//    public Long getCreatedBy() {
//        return CreatedBy;
//    }
//
//    public void setCreatedBy(Long createdBy) {
//        CreatedBy = createdBy;
//    }
//
//    public Long getUpdatedBy() {
//        return UpdatedBy;
//    }
//
//    public void setUpdatedBy(Long updatedBy) {
//        UpdatedBy = updatedBy;
//    }
//
//    public Boolean getDeleted() {
//        return IsDeleted;
//    }
//
//    public void setDeleted(Boolean deleted) {
//        IsDeleted = deleted;
//    }
//
//    public Boolean getActive() {
//        return IsActive;
//    }
//
//    public void setActive(Boolean active) {
//        IsActive = active;
//    }
//
//    public Date getCreatedOn() {
//        return CreatedOn;
//    }
//
//    public void setCreatedOn(Date createdOn) {
//        CreatedOn = createdOn;
//    }
//
//    public Date getUpdatedOn() {
//        return UpdatedOn;
//    }
//
//    public void setUpdatedOn(Date updatedOn) {
//        UpdatedOn = updatedOn;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
}