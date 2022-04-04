package com.azure.laundry.laundry.controllers;

import com.azure.laundry.laundry.CommonResponse;
import com.azure.laundry.laundry.models.Service;
import com.azure.laundry.laundry.repository.ServiceRepository;
import com.azure.laundry.laundry.service.LaundryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/service")
@SecurityRequirement(name = "jwtAuth")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ServiceController {

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    private LaundryService laundryService;

    @GetMapping()
    public ResponseEntity<?> getServiceList(){

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatusCode(HttpStatus.OK.value());
        commonResponse.setData(serviceRepository.findAll());
        commonResponse.setMessage("success");
        return  new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getServiceDetailById(@PathVariable Long serviceId){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatusCode(HttpStatus.OK.value());
        commonResponse.setData(laundryService.findServiceById(serviceId));
        return  new ResponseEntity<>(commonResponse,HttpStatus.OK);
    }

    // Removed From here if it is Migrate to another admin service
    // Or Add Admin or Moderator Role

    /*
    @PostMapping()
    public ResponseEntity<?> saveService(@RequestBody Service service){
        Service ServiceResponse = laundryService.saveService();
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatusCode(HttpStatus.OK.value());
        commonResponse.setData(ServiceResponse);
        return new ResponseEntity<>(commonResponse,HttpStatus.OK);
    }*/
}
