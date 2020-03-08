package com.gdg.supplier.service;

import com.gdg.supplier.dto.CustomerDto;
import com.gdg.supplier.entity.Customer;
import com.gdg.supplier.exception.CustomerNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findByName(String name) throws CustomerNotFoundException;

    ResponseEntity getCustomers();

    //ResponseEntity addCustomer(CustomerDto customerDto);
}
