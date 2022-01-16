package com.vladimir.deliveryfood.mapper;

import com.vladimir.deliveryfood.dto.RestaurantDto;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { DishMapper.class })
public interface RestaurantMapper {

    @Mapping(target = "dishesEntities", source = "dishesDto")
    RestaurantEntity restaurantToEntity(RestaurantDto restaurantDto);

    @InheritInverseConfiguration
    @Mapping(target = "logo", ignore = true)  // multipart file не нужно мамить в entity
    RestaurantDto restaurantToDto(RestaurantEntity restaurantEntity);
}
