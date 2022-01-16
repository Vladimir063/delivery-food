package com.vladimir.deliveryfood.mapper;

import com.vladimir.deliveryfood.dto.OrderDto;
import com.vladimir.deliveryfood.entity.OrderEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(target = "restaurantId", source = "restaurantEntity.id")
    @Mapping(target = "restaurantName", source = "restaurantEntity.name")
    @Mapping(target = "timeOfDelivery", source = "restaurantEntity.timeOfDelivery")
    @Mapping(target = "costOfDelivery", source = "restaurantEntity.costOfDelivery")
    @Mapping(target = "dishName", source = "dishEntity.name")
    @Mapping(target = "dishPrice", source = "dishEntity.price")
    OrderDto orderToDto(OrderEntity orderEntity);



    @InheritInverseConfiguration
    @Mapping(target = "timeCreateOrder", ignore = true)
    OrderEntity orderToEntity(OrderDto orderDto);





}
