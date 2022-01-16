package com.vladimir.deliveryfood.repository.impl;

import com.vladimir.deliveryfood.entity.RestaurantEntity;
import com.vladimir.deliveryfood.repository.RestaurantCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RestaurantCustomRepositoryIml implements RestaurantCustomRepository {

    private final EntityManager entityManager;

    public RestaurantCustomRepositoryIml(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RestaurantEntity updateRestaurant(RestaurantEntity restaurantEntity) {
        return entityManager.merge(restaurantEntity);
    }
}
