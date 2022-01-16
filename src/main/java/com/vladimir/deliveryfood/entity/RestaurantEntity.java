package com.vladimir.deliveryfood.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"dishEntity", "discountsEntity"})
@Builder
@Entity
@Table(name = "restaurants")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double rating;

    private Integer costOfDelivery;

    private Integer timeOfDelivery;

    private Integer averageCheck;

    private String logoUrl;

    private String cuisine;

    @OneToMany(mappedBy = "restaurantEntity")
    private List<DishEntity> dishesEntities;


}
