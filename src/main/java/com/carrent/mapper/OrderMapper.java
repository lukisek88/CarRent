package com.carrent.mapper;

import com.carrent.dao.AdditionalEquipmentDao;
import com.carrent.dao.CarDao;
import com.carrent.dao.UserDao;
import com.carrent.domain.Order;
import com.carrent.dto.CreateOrderDto;
import com.carrent.dto.OrderDto;
import com.carrent.exception.CarNotFoundException;
import com.carrent.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class OrderMapper {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CarDao carDao;

    @Autowired
    private AdditionalEquipmentDao additionalEquipmentDao;

    public Order mapToOrder(final CreateOrderDto createOrderDto) throws UserNotFoundException, CarNotFoundException {
        Order order = new Order(
                createOrderDto.getDateOfCarRental(),
                createOrderDto.getDateOfReturnCar(),
                userDao.findById(createOrderDto.getUserId()).orElseThrow(UserNotFoundException::new),
                carDao.findById(createOrderDto.getCarId()).orElseThrow(CarNotFoundException::new));
        order.setEquipments(createOrderDto.getEquipments());
        return order;
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orderList) {
        return orderList.stream()
                .map(o -> new OrderDto(
                        o.getId(),
                        o.getOrderNumber(),
                        o.getDateOfOrder(),
                        o.getDateOfCarRental(),
                        o.getDateOfReturnCar(),
                        o.isStatusOrder(),
                        o.getPrize(),
                        o.getUser().getId(),
                        o.getCar().getId(),
                        o.getEquipments()))
                .collect(Collectors.toList());
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getDateOfOrder(),
                order.getDateOfCarRental(),
                order.getDateOfReturnCar(),
                order.isStatusOrder(),
                order.getPrize(),
                order.getUser().getId(),
                order.getCar().getId(),
                order.getEquipments());
    }
}