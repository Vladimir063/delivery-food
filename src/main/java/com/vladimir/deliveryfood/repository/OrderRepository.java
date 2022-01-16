package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.entity.OrderEntity;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
