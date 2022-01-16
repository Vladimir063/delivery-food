package com.vladimir.deliveryfood.services;

import com.vladimir.deliveryfood.dto.DishDto;
import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import com.vladimir.deliveryfood.mapper.DishMapper;
import com.vladimir.deliveryfood.repository.DishRepository;
import com.vladimir.deliveryfood.repository.RestaurantRepository;
import com.vladimir.deliveryfood.services.impl.DishServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class DishServiceTest {

    @InjectMocks
    DishServiceImpl dishService;

    @Mock
    DishRepository dishRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    DishMapper dishMapper;

    @Test
    void findDishesByRestaurantId() {
        // given
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);

        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1L);
        ArrayList<DishEntity> dishEntities = new ArrayList<>(List.of(dishEntity));

        DishDto dishDto = new DishDto();
        dishDto.setId(1L);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantEntity));
        when(dishRepository.findDishEntitiesByRestaurantEntity(restaurantEntity)).thenReturn(dishEntities);
        when(dishMapper.dishToDto(dishEntity)).thenReturn(dishDto);

        // when
        List<DishDto> dishes = dishService.findDishesByRestaurantId(1L);

        // then
        assertThat(dishes.get(0).getId()).isEqualTo(1L);

    }

    @Test
    void findDishDtoById() {
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1L);

        DishDto dishDto = new DishDto();
        dishDto.setId(1L);

        when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishEntity));
        when(dishMapper.dishToDto(dishEntity)).thenReturn(dishDto);

        DishDto dishDtoById = dishService.findDishDtoById(1L);

        assertThat(dishDtoById.getId()).isEqualTo(dishDto.getId());

    }



    @Test
    void deleteDish() {
        dishService.deleteDish(100L);

        verify(dishRepository, times(1)).deleteById(100L);
    }
}