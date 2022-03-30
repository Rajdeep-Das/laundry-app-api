package com.azure.laundry.laundry.repository;

import com.azure.laundry.laundry.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Override
    List<Service> findAll();
}