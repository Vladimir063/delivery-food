package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {


    List<DishEntity> findDishEntitiesByRestaurantEntity(RestaurantEntity restaurantEntity);

    // перегруженный метод для показа сортировки
    List<DishEntity> findDishEntitiesByRestaurantEntity(RestaurantEntity restaurantEntity, Sort sort );

    Optional<DishEntity> findByName(String name);
}
