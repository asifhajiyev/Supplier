package com.gdg.supplier.repository;

import com.gdg.supplier.entity.Product;
import com.gdg.supplier.exception.ProductNotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findByName(String name) throws ProductNotFoundException;

    @Query(value = "SELECT * FROM PRODUCTS p WHERE p.name = ?1", nativeQuery = true)
    Product findByProductName(String name);

}
