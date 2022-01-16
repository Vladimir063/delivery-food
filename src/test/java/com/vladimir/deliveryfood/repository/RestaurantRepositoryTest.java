package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.entity.RestaurantEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // для использования БД не из памяти
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;


    @Test
    void findByName() {
        RestaurantEntity restaurantEntity = restaurantRepository.findByName("KFC").get();

        assertThat(restaurantEntity.getName()).isEqualTo("KFC");
    }

    @Test
    void findByCuisine() {
        List<RestaurantEntity> restaurantEntityList = restaurantRepository.findByCuisine("SUSHI");

        assertThat(restaurantEntityList.get(0).getCuisine()).isEqualTo("SUSHI");
    }
}