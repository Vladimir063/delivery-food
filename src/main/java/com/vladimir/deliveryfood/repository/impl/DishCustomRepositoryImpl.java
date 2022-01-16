package com.vladimir.deliveryfood.repository.impl;

import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.repository.DishCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DishCustomRepositoryImpl implements DishCustomRepository {

    private final EntityManager entityManager;

    public DishCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public DishEntity updateDish(DishEntity dishEntity) {
        return entityManager.merge(dishEntity);
    }
}
