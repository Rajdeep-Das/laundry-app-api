package com.azure.laundry.laundry.controllers;

import com.azure.laundry.laundry.CommonResponse;
import com.azure.laundry.laundry.payload.response.ProductResponse;
import com.azure.laundry.laundry.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/product")
@SecurityRequirement(name = "jwtAuth")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getProductById(@PathVariable Long serviceId) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatus(HttpStatus.OK.value());
        commonResponse.setMessage("success");

        List<ProductResponse> productResponses = productService.getAllProductAndPriceByProductAndServiceId(serviceId);
        commonResponse.setData(productResponses);

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
