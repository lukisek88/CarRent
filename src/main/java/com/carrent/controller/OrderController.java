package com.carrent.controller;

import com.carrent.domain.Order;
import com.carrent.dto.CreateOrderDto;
import com.carrent.dto.OrderDto;
import com.carrent.exception.AdditionalEquipmentNotFoundException;
import com.carrent.exception.CarNotFoundException;
import com.carrent.exception.OrderNotFoundException;
import com.carrent.exception.UserNotFoundException;
import com.carrent.mapper.OrderMapper;
import com.carrent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Transactional
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public List<OrderDto> getOrders(){
        return orderMapper.mapToOrderDtoList(orderService.getOrders());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        return orderMapper.mapToOrderDto(orderService.getOrder(orderId).orElseThrow(OrderNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    public OrderDto createOrder (@RequestBody CreateOrderDto createOrderDto) throws CarNotFoundException, UserNotFoundException {
        return orderMapper.mapToOrderDto(orderService.saveOrder(orderMapper.mapToOrder(createOrderDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/orders/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{orderID}/status/{status}")
    public OrderDto updateStatusOrder (@PathVariable Long orderID, @PathVariable boolean status) throws OrderNotFoundException {
        Optional<Order> order = orderService.getOrder(orderID);
        order.get().setStatusOrder(status);
        return orderMapper.mapToOrderDto(orderService.saveUpdateStatusOrder(order.orElseThrow(OrderNotFoundException::new)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/activation/{orderID}")
    public void activateOrder (@PathVariable Long orderID) throws OrderNotFoundException, CarNotFoundException, AdditionalEquipmentNotFoundException, UserNotFoundException {
        orderService.activatingOrder(orderID);
    }
}