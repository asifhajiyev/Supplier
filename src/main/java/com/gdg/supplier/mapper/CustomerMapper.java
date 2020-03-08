package com.gdg.supplier.mapper;

import com.gdg.supplier.dto.CustomerDto;
import com.gdg.supplier.dto.ProductDto;
import com.gdg.supplier.entity.Customer;
import com.gdg.supplier.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {
    public static Customer dtoToEntity(CustomerDto customerDto) {
        if (customerDto == null) {
            return null;
        }
        Customer customer=new Customer();
        customer.setName(customerDto.getName());
        return customer;
    }

    public static CustomerDto entityToDto(Customer customer){
        if (customer == null) {
            return null;
        }
        CustomerDto customerDto=new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setOrders(OrderMapper.entityToDtoAsList(customer.getOrders()));
        return customerDto;
    }
}
