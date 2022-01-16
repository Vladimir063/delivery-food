package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.DeliveryFoodApplication;
import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // для использования БД не из памяти
class DishRepositoryTest {

    @Autowired
    DishRepository dishRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void findByName() {
        Optional<DishEntity> maybeDish = dishRepository.findByName("Чизбургер");

        Assertions.assertThat(maybeDish.get().getName()).isEqualTo("Чизбургер");
    }

    @Test
    void findDishEntitiesByRestaurantEntity(){
        Optional<RestaurantEntity> maybeRestaurant = restaurantRepository.findById(1L);

        List<DishEntity> dishEntitiesByRestaurantEntity =
                dishRepository.findDishEntitiesByRestaurantEntity(maybeRestaurant.get());

        Assertions.assertThat(dishEntitiesByRestaurantEntity).isNotNull();

        Assertions.assertThat(dishEntitiesByRestaurantEntity.get(0).getRestaurantEntity()).isEqualTo(maybeRestaurant.get());
    }

}