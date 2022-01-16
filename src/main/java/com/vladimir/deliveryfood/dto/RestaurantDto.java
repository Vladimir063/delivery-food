package com.vladimir.deliveryfood.dto;

import com.vladimir.deliveryfood.entity.enums.Cuisine;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RestaurantDto {

    private Long id;

    @NotBlank(message = "Имя ресторана не может быть пустым")
    private String name;

    private Double rating;

    @NotNull(message = "Введи стоимость доставки")
    private Integer costOfDelivery;

    private Integer timeOfDelivery;

    private Integer averageCheck;

    private String logoUrl;

    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;

    private List<DishDto> dishesDto;

    private MultipartFile logo;




}
