package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.dto.OrderDto;
import com.vladimir.deliveryfood.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCustomRepository {

     OrderEntity updateOrder(OrderEntity entity);
}
