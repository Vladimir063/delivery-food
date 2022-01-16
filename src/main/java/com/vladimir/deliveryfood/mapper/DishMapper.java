package com.vladimir.deliveryfood.mapper;


import com.vladimir.deliveryfood.dto.DishDto;
import com.vladimir.deliveryfood.entity.DishEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishMapper {

    @Mapping(target = "restaurantId", source = "restaurantEntity.id")
    @Mapping(target = "restaurantName", source = "restaurantEntity.name")
    @Mapping(target = "image", ignore = true)
    DishDto dishToDto(DishEntity dishEntity);

    @InheritInverseConfiguration
    DishEntity dishToEntity(DishDto dishDto);

}
