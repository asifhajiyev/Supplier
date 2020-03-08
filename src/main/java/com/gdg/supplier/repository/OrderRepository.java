package com.gdg.supplier.repository;

import com.gdg.supplier.entity.Customer;
import com.gdg.supplier.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByOrderedProductNameAndCustomerAndSentIsFalse(String orderedProductName, Customer customer);

    List<Order> findAllByOrderedProductAmountIsLessThanEqualAndOrderedProductNameAndSentIsFalse(Long orderedProductAmount, String orderedProductName);

    Optional<Order> findById(Long id);
}
