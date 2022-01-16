package com.vladimir.deliveryfood.services;

import com.vladimir.deliveryfood.dto.RestaurantDto;


import java.util.List;

public interface RestaurantService {

    List<RestaurantDto> findAllDto();


    List<RestaurantDto> findAllDtoSort(String[] sort);


    List<RestaurantDto> findByCuisine(String cuisine);


    RestaurantDto findRestaurantDtoById(Long id);


    RestaurantDto saveOrUpdateRestaurant(RestaurantDto restaurantDto);


    void deleteRestaurant(Long id);
}
