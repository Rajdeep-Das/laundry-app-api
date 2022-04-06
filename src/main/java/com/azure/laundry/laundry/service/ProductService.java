package com.azure.laundry.laundry.service;

import com.azure.laundry.laundry.models.Product;
import com.azure.laundry.laundry.models.ProductPrice;
import com.azure.laundry.laundry.payload.response.ProductResponse;
import com.azure.laundry.laundry.repository.ProductPriceRepository;
import com.azure.laundry.laundry.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductPriceRepository productPriceRepository;

    public List<Product> findProductByServiceId(Long ServiceId){
        return productRepository.findAllProductByServiceId(ServiceId);
    }

    public ProductPrice findProductPriceByServiceIdAndProductId(Long serviceId, Long productId){
        return productPriceRepository.findAllProductPriceByServiceIdAndProductId(serviceId,productId);
    }

    // Refactor DB Access for Better performance, multiple db hit occurred here
    public List<ProductResponse> getAllProductAndPriceByProductAndServiceId(Long serviceId){

        List<Product> productList = this.findProductByServiceId(serviceId);
        List<ProductResponse>  productResponsesList = new ArrayList<>();

        productList.forEach(product -> {
            ProductPrice productPrice =  this.findProductPriceByServiceIdAndProductId(serviceId,product.getId());

            if(productPrice != null) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(product.getId());
                productResponse.setName(product.getName());
                productResponse.setIcon(product.getIcon());
                productResponse.setGender(product.getGender());
                productResponse.setRank(product.getRank());
                productResponse.setPrice(productPrice.getPrice());
                productResponsesList.add(productResponse);
            }
        });
        return productResponsesList;
    }

}
