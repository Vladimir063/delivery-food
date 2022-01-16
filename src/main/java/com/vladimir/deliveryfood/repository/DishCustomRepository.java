package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.entity.DishEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DishCustomRepository {

    DishEntity updateDish(DishEntity dishEntity);
}
