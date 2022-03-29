package com.azure.laundry.laundry.controllers;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.azure.laundry.laundry.CommonResponse;
import com.azure.laundry.laundry.models.Address;
import com.azure.laundry.laundry.models.User;
import com.azure.laundry.laundry.payload.request.AddressRequest;
import com.azure.laundry.laundry.repository.AddressRepository;
import com.azure.laundry.laundry.repository.UserRepository;
import com.azure.laundry.laundry.security.services.UserDetailsImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // Lombok
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "jwtAuth")
public class UserController {

    @Autowired
    UserRepository userRepository;
   
    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/address")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAddressByUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
       
        Optional<User> userQueryData = userRepository.findById(userDetails.getId());
        
        if(userQueryData.isPresent() &&  (userQueryData.get().getAddress()!= null)){
            
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setData(userQueryData.get().getAddress());
            commonResponse.setStatusCode(200);
            commonResponse.setMessage("success");
            commonResponse.setDescription("");
            log.info("Adress Found");
            return new ResponseEntity<>(commonResponse,HttpStatus.OK);
        }else{
            log.info("No Adress Found");
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setData(null);
            commonResponse.setStatusCode(404);
            commonResponse.setMessage("failed");
            commonResponse.setDescription("No Address Set");
            return new ResponseEntity<>(commonResponse,HttpStatus.NOT_FOUND);
        }
    
        
    }

    @PostMapping("/address")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressRequest addressRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
        Optional<User> userQueryData = userRepository.findById(userDetails.getId());

        if(userQueryData.isPresent()){
            User user = userQueryData.get();

            Address uAddress = user.getAddress();
            if(uAddress != null){
                user.setAddress(null);
                userRepository.save(user);
                addressRepository.deleteById(uAddress.getId());
            }
            
            ModelMapper modelMapper = new ModelMapper();
            Address address = modelMapper.map(addressRequest, Address.class);
            user.setAddress(address);
            userRepository.save(user);
            //modelMapper.createTypeMap(AddressRequest.class,Address.class);
            // modelMapper.validate();

            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setData(address);
            commonResponse.setStatusCode(HttpStatus.CREATED.value());
            commonResponse.setMessage("success");
            commonResponse.setDescription("");

            return new ResponseEntity<>(commonResponse,HttpStatus.CREATED);
        }

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(null);
        commonResponse.setStatusCode(400);
        commonResponse.setMessage("failed");
        commonResponse.setDescription("Invalid Request");
        return new ResponseEntity<>(commonResponse,HttpStatus.BAD_REQUEST);
    }
}
