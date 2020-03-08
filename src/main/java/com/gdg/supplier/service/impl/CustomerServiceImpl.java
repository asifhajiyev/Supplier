package com.gdg.supplier.service.impl;

import com.gdg.supplier.dto.CustomerDto;
import com.gdg.supplier.entity.Customer;
import com.gdg.supplier.exception.CustomerNotFoundException;
import com.gdg.supplier.mapper.CustomerMapper;
import com.gdg.supplier.repository.CustomerRepository;
import com.gdg.supplier.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findByName(String name) throws CustomerNotFoundException {
        return customerRepository.findByName(name);
    }

    @Override
    public ResponseEntity getCustomers() {
        List<CustomerDto> customers = new ArrayList<>();
        for (Customer c : customerRepository.findAll()) {
            customers.add(CustomerMapper.entityToDto(c));
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

   /* @Override
    public ResponseEntity addCustomer(CustomerDto customerDto) {
        customerRepository.save(CustomerMapper.dtoToEntity(customerDto));
        return new ResponseEntity<>("Customer created", HttpStatus.CREATED);
    }*/
}
