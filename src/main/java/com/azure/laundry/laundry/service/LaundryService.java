package com.azure.laundry.laundry.service;

import com.azure.laundry.laundry.models.Product;
import com.azure.laundry.laundry.models.Service;
import com.azure.laundry.laundry.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class LaundryService {
    @Autowired
    private ServiceRepository serviceRepository;

    public Service saveService(){
        List<Product> productList = new ArrayList<>();

        Service service = serviceRepository.getById(1L);


        /* Mock Data Later Code Will Be Removed */
        // create products
        Product product = Product.builder().Name("Shirt").Gender("MALE").Rank(0).build();
        Product product1 = Product.builder().Name("PoloShirt").Gender("MALE").Rank(0).build();
        Product product2 = Product.builder().Name("T- Shirt").Gender("FEMALE").Rank(0).build();

       // data for OneToMany
        productList.add(product);
        productList.add(product1);
        productList.add(product2);

        //Many to One
        product.setService(service);
        product1.setService(service);
        product2.setService(service);

        service.setProductList(productList);

        service = serviceRepository.save(service);
        return service;

    }
    public Service findServiceById(Long serviceId) {
        Service service = serviceRepository.findById(serviceId).get();
        return service;
    }
}
