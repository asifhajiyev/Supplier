package com.gdg.supplier.service.impl;

import com.gdg.supplier.client.BuyerFeignClient;
import com.gdg.supplier.dto.ProductDto;
import com.gdg.supplier.entity.Order;
import com.gdg.supplier.entity.Product;
import com.gdg.supplier.exception.ProductNotFoundException;
import com.gdg.supplier.mapper.ProductMapper;
import com.gdg.supplier.repository.OrderRepository;
import com.gdg.supplier.repository.ProductRepository;
import com.gdg.supplier.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private BuyerFeignClient buyerFeignClient;

    public ProductServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, BuyerFeignClient buyerFeignClient) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.buyerFeignClient = buyerFeignClient;
    }

    @Override
    public Optional<Product> findByName(String productName) throws ProductNotFoundException {
        return productRepository.findByName(productName);
    }

    @Override
    public ResponseEntity getProducts() {
        List<ProductDto> products = new ArrayList<>();
        for (Product p : productRepository.findAll()) {
            products.add(ProductMapper.entityToDto(p));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity addProduct(ProductDto productDto) {
        Product product = findByProductName(productDto.getName());

        if (product != null) {
            product.setAmount(product.getAmount() + productDto.getAmount());
        } else {
            product = ProductMapper.dtoToEntity(productDto);
        }

        productRepository.save(product);
        String st = "Product added ";

        // looking for unsent orders
        List<Order> orders = orderRepository.findAllByOrderedProductAmountIsLessThanEqualAndOrderedProductNameAndSentIsFalse(
                product.getAmount(), product.getName());
        for (Order o : orders) {
            String customerName = o.getCustomer().getName();

            String message = "Dear," + customerName + " we got " + product.getName() + ", if you want we can send now....... ";
            String askForOrder = "If you want send 'yes' or if you don't want send 'no' to this url....... ";
            String url = "http://localhost:8080/supplier/api/orders/" + o.getId() + "/?want-later=";

            buyerFeignClient.notifyCustomer(message + askForOrder + url);
            st = "Product added and " + orders.size() + " customers notified";
        }
        return new ResponseEntity<>(st, HttpStatus.CREATED);
    }

    @Override
    public Product findByProductName(String name) {
        return productRepository.findByProductName(name);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product = null;
        try {
            product = productRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));
        } catch (ProductNotFoundException e) {
            e.getMessage();
        }
        return ProductMapper.entityToDto(product);
    }

    @Override
    public String deleteProductById(String id) {
        String response;
        ProductDto productDto = getProductById(id);
        if (productDto == null) {
            response = "Product doesn't exist";
        } else {
            productRepository.deleteById(Long.valueOf(id));
            response = "Deleted";
        }
        return response;
    }
}
