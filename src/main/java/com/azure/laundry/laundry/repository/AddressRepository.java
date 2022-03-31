package com.azure.laundry.laundry.repository;


import com.azure.laundry.laundry.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT u FROM Address u WHERE u.id = ?1")
    public Address findByAddressId(Long id);
}
