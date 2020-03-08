package com.gdg.supplier.repository;

import com.gdg.supplier.entity.Customer;
import com.gdg.supplier.entity.Product;
import com.gdg.supplier.exception.CustomerNotFoundException;
import com.gdg.supplier.exception.ProductNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByName(String name) throws CustomerNotFoundException;
}
