package com.gdg.supplier.mapper;

import com.gdg.supplier.dto.OrderDto;
import com.gdg.supplier.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static Order dtoToEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        Order order = new Order();
        order.setOrderedProductName(orderDto.getOrderedProductName());
        order.setOrderedProductAmount(orderDto.getOrderedProductAmount());
        order.setSent(false);
        return order;
    }

    public static OrderDto entityToDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName(order.getCustomer().getName());
        orderDto.setOrderedProductName(order.getOrderedProductName());
        orderDto.setOrderedProductAmount(order.getOrderedProductAmount());
        orderDto.setSent(order.isSent());
        return orderDto;
    }

    public static List<OrderDto> entityToDtoAsList(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order o : orders) {
            OrderDto orderDto = new OrderDto();
            orderDto.setCustomerName(o.getCustomer().getName());
            orderDto.setOrderedProductName(o.getOrderedProductName());
            orderDto.setOrderedProductAmount(o.getOrderedProductAmount());
            orderDto.setSent(o.isSent());
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}
