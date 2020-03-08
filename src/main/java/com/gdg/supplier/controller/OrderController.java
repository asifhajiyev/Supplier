package com.gdg.supplier.controller;

import com.gdg.supplier.dto.OrderDto;
import com.gdg.supplier.entity.Order;
import com.gdg.supplier.exception.CustomerNotFoundException;
import com.gdg.supplier.exception.ProductNotFoundException;
import com.gdg.supplier.model.CustomResponse;
import com.gdg.supplier.repository.OrderRepository;
import com.gdg.supplier.service.impl.OrderServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "Gets all orders")
    @GetMapping
    public ResponseEntity getOrders() {
        return orderService.getOrders();
    }

    @ApiOperation(value = "Gets order by given id")
    @GetMapping("{id}")
    public ResponseEntity getOrder(@PathVariable Long id) {
        return new ResponseEntity(orderService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "When client send order this method works")
    @PostMapping("add-order")
    public CustomResponse addOrder(@RequestBody OrderDto orderDto) {
        return orderService.addOrder(orderDto);
    }

    @ApiOperation(value = "Sends order when it is not available for the first time client send request as 'yes' or 'no'")
    @PostMapping("{id}")
    public ResponseEntity sendOrder(@PathVariable Long id, @RequestParam("want-later") String wantLater) {
        return new ResponseEntity(orderService.sendOrder(id, wantLater), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes order by given id")
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteOrderById(@PathVariable String id) {
        return new ResponseEntity(orderService.deleteOrderById(id), HttpStatus.OK);
    }
}
