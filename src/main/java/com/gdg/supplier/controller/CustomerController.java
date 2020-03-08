package com.gdg.supplier.controller;

import com.gdg.supplier.dto.CustomerDto;
import com.gdg.supplier.service.impl.CustomerServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("customers")
public class CustomerController {

    private CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Gets all customers")
    @GetMapping
    public ResponseEntity getCustomers() {
        return customerService.getCustomers();
    }

   /* @PostMapping("/add-customer")
    public ResponseEntity addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
    }*/
}
