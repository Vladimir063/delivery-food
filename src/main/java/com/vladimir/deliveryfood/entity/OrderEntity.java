package com.vladimir.deliveryfood.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerPhone;

    private String address;

    private Integer amount;

    private Integer totalCost;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private DishEntity dishEntity;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantEntity;

    @Column(name = "time_create_order", columnDefinition = "TIMESTAMP")
    private LocalDateTime timeCreateOrder;




}
