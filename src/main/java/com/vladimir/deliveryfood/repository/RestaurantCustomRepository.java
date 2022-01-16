package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantCustomRepository {

    RestaurantEntity  updateRestaurant(RestaurantEntity entity);
}
