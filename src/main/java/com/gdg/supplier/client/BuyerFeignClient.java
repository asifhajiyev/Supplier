package com.gdg.supplier.client;


import com.gdg.supplier.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "supplier-client", url = "http://localhost:8090/buyer/api")
public interface BuyerFeignClient {

    @PostMapping("/notifications/send-notification")
    ResponseEntity notifyCustomer(String text);

    @PostMapping("/products/accept-product")
    ResponseEntity sendProduct(Product product);
}
