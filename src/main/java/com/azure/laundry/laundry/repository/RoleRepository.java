package com.azure.laundry.laundry.repository;

import com.azure.laundry.laundry.models.ERole;
import com.azure.laundry.laundry.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
