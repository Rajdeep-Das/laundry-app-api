package com.azure.laundry.laundry.controllers;

import com.azure.laundry.laundry.CommonResponse;
import com.azure.laundry.laundry.repository.ServiceRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/service")
@SecurityRequirement(name = "jwtAuth")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ServiceController {

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping()
    public ResponseEntity<?> getServiceList(){

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatusCode(HttpStatus.OK.value());
        commonResponse.setData(serviceRepository.findAll());
        commonResponse.setMessage("success");
        return  new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
