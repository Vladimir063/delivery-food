package com.vladimir.deliveryfood.dto;

import com.vladimir.deliveryfood.entity.RestaurantEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DishDto {


    private Long id;

    @NotBlank(message = "Введите название блюда")
    private String name;

    @NotNull(message = "Введите цену")
    private Integer price;

    private Integer kcal;

    private Integer weight;

    private String description;

    private MultipartFile image;

    private String imageUrl;

    private Long restaurantId;

    private String restaurantName;



}
