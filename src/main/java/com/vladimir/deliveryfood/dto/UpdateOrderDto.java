package com.vladimir.deliveryfood.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateOrderDto {

    private Long id;

    private String customerName;

    private String customerPhone;

    private String address;

    private Integer amount;

    private List<DishDto> dishesDto;

    private String dishName;

    private Integer dishPrice;

    private Long restaurantId;

    private String restaurantName;

    private Integer timeOfDelivery;

    private Integer costOfDelivery;

    private Integer totalCost;

    private LocalDateTime timeCreateOrder;
}
