package com.vladimir.deliveryfood.services;

import com.vladimir.deliveryfood.dto.RestaurantDto;
import com.vladimir.deliveryfood.entity.enums.Cuisine;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import com.vladimir.deliveryfood.mapper.RestaurantMapper;
import com.vladimir.deliveryfood.repository.RestaurantRepository;
import com.vladimir.deliveryfood.services.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantServiceImplTest {

    @InjectMocks
    RestaurantServiceImpl restaurantServiceImpl;

    @Mock
    RestaurantMapper restaurantMapper;

    @Mock
    RestaurantRepository restaurantRepository;


    @Test
    void findAllDto() {
        // given
        RestaurantEntity restaurantEntity1 = new RestaurantEntity();
        restaurantEntity1.setId(1L);
        RestaurantEntity restaurantEntity2 = new RestaurantEntity();
        restaurantEntity2.setId(2L);
        ArrayList<RestaurantEntity> restaurantEntities = new ArrayList<>(List.of(restaurantEntity1, restaurantEntity2));

        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);

        RestaurantDto restaurantDto1 = new RestaurantDto();
        restaurantDto1.setId(1L);
        RestaurantDto restaurantDto2 = new RestaurantDto();
        restaurantDto2.setId(2L);


        when(restaurantMapper.restaurantToDto(restaurantEntity1)).thenReturn(restaurantDto1);
        when(restaurantMapper.restaurantToDto(restaurantEntity2)).thenReturn(restaurantDto2);

        // when
        List<RestaurantDto> dtoList = restaurantServiceImpl.findAllDto();

        // then
        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getId()).isEqualTo(1L);
        assertThat(dtoList.get(1).getId()).isEqualTo(2L);
        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantMapper, times(2)).restaurantToDto(any());
    }

    @Test
    void findAllDtoSort() {
        // given
        RestaurantEntity restaurantEntity1 = new RestaurantEntity();
        restaurantEntity1.setId(5L);
        restaurantEntity1.setName("Burger King");
        RestaurantEntity restaurantEntity2 = new RestaurantEntity();
        restaurantEntity2.setId(10L);
        restaurantEntity1.setName("KFC");
        ArrayList<RestaurantEntity> restaurantEntities = new ArrayList<>(List.of(restaurantEntity2, restaurantEntity1));

        Sort sort = Sort.by(Sort.Direction.DESC, "name");

        when(restaurantRepository.findAll(sort)).thenReturn(restaurantEntities);

        RestaurantDto restaurantDto1 = new RestaurantDto();
        restaurantDto1.setId(5L);
        restaurantDto1.setName("Burger King");
        RestaurantDto restaurantDto2 = new RestaurantDto();
        restaurantDto2.setId(10L);
        restaurantDto2.setName("KFC");
        ArrayList<RestaurantDto> restaurantDtos = new ArrayList<>(List.of(restaurantDto1, restaurantDto2));

        when(restaurantMapper.restaurantToDto(restaurantEntity1)).thenReturn(restaurantDto1);
        when(restaurantMapper.restaurantToDto(restaurantEntity2)).thenReturn(restaurantDto2);

        // when
        List<RestaurantDto> allDtoSort = restaurantServiceImpl.findAllDtoSort(new String[]{"name", "desc"});

        // then
        assertThat(allDtoSort).hasSize(2);
        assertThat(allDtoSort.get(0).getName()).isEqualTo("KFC");
        assertThat(allDtoSort.get(1).getName()).isEqualTo("Burger King");

    }


    @Test
    void findByCuisine() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setCuisine("SUSHI");
        List<RestaurantEntity> restaurantEntityList = new ArrayList<>(List.of(restaurantEntity));

        when(restaurantRepository.findByCuisine("SUSHI")).thenReturn(restaurantEntityList);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setCuisine(Cuisine.SUSHI);

        when(restaurantMapper.restaurantToDto(any())).thenReturn(restaurantDto);

        List<RestaurantDto> restaurantDtoList = restaurantServiceImpl.findByCuisine("SUSHI");

        assertThat(restaurantDtoList).hasSize(1);
    }

    @Test
    void findRestaurantDtoById() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        Optional<RestaurantEntity> maybeRestaurant = Optional.of(restaurantEntity);

        when(restaurantRepository.findById(anyLong())).thenReturn(maybeRestaurant);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(1L);

        when(restaurantMapper.restaurantToDto(any())).thenReturn(restaurantDto);

        RestaurantDto restaurantDtoById = restaurantServiceImpl.findRestaurantDtoById(1L);

        assertNotNull(restaurantDtoById, "Null restaurant returned");
        verify(restaurantRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveOrUpdateRestaurant() {
        // given
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName("CoffeeCup");
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName("CoffeeCup");

        when(restaurantMapper.restaurantToEntity(restaurantDto)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);

        when(restaurantMapper.restaurantToDto(restaurantEntity)).thenReturn(restaurantDto);

        // when
        RestaurantDto dtoSave = restaurantServiceImpl.saveOrUpdateRestaurant(restaurantDto);

        // then
        assertThat(dtoSave.getName()).isEqualTo("CoffeeCup");

    }

    @Test
    void deleteRestaurant() {
        restaurantServiceImpl.deleteRestaurant(55L);

        verify(restaurantRepository, times(1)).deleteById(anyLong());
    }
}