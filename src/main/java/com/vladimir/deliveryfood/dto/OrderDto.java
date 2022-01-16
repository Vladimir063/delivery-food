package com.vladimir.deliveryfood.dto;

import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto {

    private Long id;

    @NotBlank(message = "Введите имя покупателя")
    private String customerName;

    private String customerPhone;

    @NotBlank(message = "Введите адрес")
    private String address;

    @NotNull(message = "Введите количество")
    private Integer amount;

    private String dishName;

    private Integer dishPrice;

    private Long restaurantId;

    private String restaurantName;

    private Integer timeOfDelivery;

    private Integer costOfDelivery;

    private Integer totalCost;

    private LocalDateTime timeCreateOrder;

    private String selectDish;  // блюдо, которое быбрал пользователь
}
