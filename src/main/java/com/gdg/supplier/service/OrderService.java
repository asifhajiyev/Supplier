package com.gdg.supplier.service;

import com.gdg.supplier.dto.OrderDto;
import com.gdg.supplier.entity.Customer;
import com.gdg.supplier.entity.Order;
import com.gdg.supplier.exception.CustomerNotFoundException;
import com.gdg.supplier.exception.OrderNotFoundException;
import com.gdg.supplier.exception.ProductNotFoundException;
import com.gdg.supplier.model.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    ResponseEntity getOrders();

    CustomResponse addOrder(OrderDto orderDto) throws ProductNotFoundException, CustomerNotFoundException;

    Order findByOrderedProductNameAndCustomerAndSentIsFalse(String orderedProductName, Customer customer);

    List<Order> findAllByOrderedProductAmountIsLessThanEqualAndOrderedProductNameAndSentIsFalse(Long orderedProductAmount, String orderedProductName);

    Order findById(Long id) throws OrderNotFoundException;

    String sendOrder(Long id, String wantLater);

    String deleteOrderById(String id);
}
