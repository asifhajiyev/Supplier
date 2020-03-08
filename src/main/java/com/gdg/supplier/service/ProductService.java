package com.gdg.supplier.service;

import com.gdg.supplier.dto.ProductDto;
import com.gdg.supplier.entity.Product;
import com.gdg.supplier.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {

    Optional<Product> findByName(String productName) throws ProductNotFoundException;

    ResponseEntity getProducts();

    ResponseEntity addProduct(ProductDto productDto);

    Product findByProductName(String name);

    ProductDto getProductById(String id);

    String deleteProductById(String id);
}
