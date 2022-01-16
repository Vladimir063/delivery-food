package com.vladimir.deliveryfood.services;

import com.vladimir.deliveryfood.dto.DishDto;
import java.util.List;

public interface DishService {

    List<DishDto> findDishesByRestaurantId(Long restaurantId);

    // для показа блюд в отсортированном виде
    List<DishDto> findDishesByRestaurantIdSort(Long restaurantId, String[] sort);


    DishDto findDishDtoById(Long id);


    DishDto addNewDish(Long restaurantId);


    DishDto saveOrUpdateDish(DishDto dishDto);


    void deleteDish(Long id);
}
