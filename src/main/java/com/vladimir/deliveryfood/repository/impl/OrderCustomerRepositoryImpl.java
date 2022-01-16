package com.vladimir.deliveryfood.repository.impl;

import com.vladimir.deliveryfood.entity.OrderEntity;
import com.vladimir.deliveryfood.repository.OrderCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
public class OrderCustomerRepositoryImpl implements OrderCustomRepository {

    private final EntityManager entityManager;

    public OrderCustomerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public OrderEntity updateOrder(OrderEntity entity) {
        return entityManager.merge(entity);
    }
}
