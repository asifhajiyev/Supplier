package com.gdg.supplier.service.impl;

import com.gdg.supplier.client.BuyerFeignClient;
import com.gdg.supplier.dto.CustomerDto;
import com.gdg.supplier.dto.OrderDto;
import com.gdg.supplier.dto.ProductDto;
import com.gdg.supplier.entity.Customer;
import com.gdg.supplier.entity.Order;
import com.gdg.supplier.entity.Product;
import com.gdg.supplier.exception.CustomerNotFoundException;
import com.gdg.supplier.exception.OrderNotFoundException;
import com.gdg.supplier.exception.ProductNotFoundException;
import com.gdg.supplier.mapper.CustomerMapper;
import com.gdg.supplier.mapper.OrderMapper;
import com.gdg.supplier.mapper.ProductMapper;
import com.gdg.supplier.model.CustomResponse;
import com.gdg.supplier.repository.CustomerRepository;
import com.gdg.supplier.repository.OrderRepository;
import com.gdg.supplier.repository.ProductRepository;
import com.gdg.supplier.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private BuyerFeignClient buyerFeignClient;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository, BuyerFeignClient buyerFeignClient) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.buyerFeignClient = buyerFeignClient;
    }

    @Override
    public ResponseEntity getOrders() {
        List<OrderDto> orders = new ArrayList<>();
        for (Order o : orderRepository.findAll()) {
            orders.add(OrderMapper.entityToDto(o));
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public CustomResponse addOrder(OrderDto orderDto) {
        CustomResponse response = new CustomResponse();
        Product product;
        Order order = OrderMapper.dtoToEntity(orderDto);

        try {
            Customer customer = customerRepository.findByName(orderDto.getCustomerName()).orElseThrow(() -> new CustomerNotFoundException("You are not our customer"));
            order.setCustomer(customer);
            product = productRepository.findByName(orderDto.getOrderedProductName()).orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));
        } catch (CustomerNotFoundException | ProductNotFoundException e) {
            response.setCode(400);
            response.setMessage(e.getMessage());
            return response;
        }
        if (product.getAmount() >= orderDto.getOrderedProductAmount()) {
            order.setSent(true);
            product.setAmount(product.getAmount() - orderDto.getOrderedProductAmount());
            response.setCode(1);
            response.setMessage("We sent your order!");
        } else {
            response.setCode(2);
            response.setMessage("We don't have enough " + orderDto.getOrderedProductName() + " for now. We will send when we get...");

            Order o = findByOrderedProductNameAndCustomerAndSentIsFalse(order.getOrderedProductName(), order.getCustomer());
            if (o != null) {
                order = o;
                order.setOrderedProductAmount(order.getOrderedProductAmount() + orderDto.getOrderedProductAmount());
                orderRepository.save(order);
                return response;
            }
        }
        orderRepository.save(order);
        return response;
    }

    @Override
    public Order findByOrderedProductNameAndCustomerAndSentIsFalse(String orderedProductName, Customer customer) {
        return orderRepository.findByOrderedProductNameAndCustomerAndSentIsFalse(orderedProductName, customer);
    }

    @Override
    public List<Order> findAllByOrderedProductAmountIsLessThanEqualAndOrderedProductNameAndSentIsFalse(Long orderedProductAmount, String orderedProductName) {
        List<Order> orders = orderRepository.findAllByOrderedProductAmountIsLessThanEqualAndOrderedProductNameAndSentIsFalse(orderedProductAmount, orderedProductName);
        return orders;
    }

    @Override
    public Order findById(Long id) {
        Order order;
        try {
            order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order doesn't exist with this id"));
        } catch (OrderNotFoundException e) {
            return null;
        }
        return order;
    }

    @Override
    public String sendOrder(Long id, String wantLater) {
        String response = "";
        Order order = findById(id);
        if (wantLater.equalsIgnoreCase("yes")) {
            order.setSent(true);
            Product p = productRepository.findByProductName(order.getOrderedProductName());
            p.setAmount(p.getAmount() - order.getOrderedProductAmount());
            productRepository.save(p);
            orderRepository.save(order);
            Product product = new Product();
            product.setName(order.getOrderedProductName());
            product.setAmount(order.getOrderedProductAmount());
            buyerFeignClient.sendProduct(product);
            response = "Customer accept and Product sent!";
        } else {
            orderRepository.delete(order);
            response = "Customer cancelled the order and order deleted permanently";
        }
        return response;
    }

    @Override
    public String deleteOrderById(String id) {
        String response;
        Order order = findById(Long.valueOf(id));
        if (order == null) {
            response = "Order doesn't exist";
        } else {
            orderRepository.deleteById(Long.valueOf(id));
            response = "Deleted";
        }
        return response;
    }


}
