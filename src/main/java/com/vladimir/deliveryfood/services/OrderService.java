package com.vladimir.deliveryfood.services;

import com.vladimir.deliveryfood.dto.OrderDto;


import java.util.List;

public interface OrderService {

    List<OrderDto> findAllOrders(String[] sort);

    // сохранение заказа шаг 1
    OrderDto saveOrderStep1(OrderDto orderDto);

    // сохранение заказа шаг 2
    OrderDto saveOrUpdateOrderStep2(OrderDto orderDto);


    OrderDto findById(Long id);


    void deleteOrder(Long id);
}
