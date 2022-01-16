package com.vladimir.deliveryfood.services.impl;

import com.vladimir.deliveryfood.dto.DishDto;
import com.vladimir.deliveryfood.dto.RestaurantDto;
import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import com.vladimir.deliveryfood.exception.DishNotFoundException;
import com.vladimir.deliveryfood.exception.RestaurantNotFoundException;
import com.vladimir.deliveryfood.mapper.DishMapper;
import com.vladimir.deliveryfood.repository.DishCustomRepository;
import com.vladimir.deliveryfood.repository.DishRepository;
import com.vladimir.deliveryfood.repository.RestaurantRepository;
import com.vladimir.deliveryfood.services.DishService;
import com.vladimir.deliveryfood.services.RestaurantService;
import com.vladimir.deliveryfood.util.CreateSort;
import com.vladimir.deliveryfood.util.FileUploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {

    private  final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;
    private final DishMapper dishMapper;
    private final DishCustomRepository dishCustomRepository;

    @Override
    public List<DishDto> findDishesByRestaurantId(Long restaurantId){
        Optional<RestaurantEntity> maybeRestaurant = restaurantRepository.findById(restaurantId);
        if (!maybeRestaurant.isPresent()){
            log.error("Restaurant not found by id = {}" , restaurantId);
            throw new RestaurantNotFoundException("Restaurant not found by id = " + restaurantId);
        }
        List<DishEntity> dishEntities =
                dishRepository.findDishEntitiesByRestaurantEntity(maybeRestaurant.get());
        return dishEntities.stream()
                .map(dishEntity -> dishMapper.dishToDto(dishEntity))
                .collect(Collectors.toList());
    }

    // для показа блюд в отсортированном виде
    @Override
    public List<DishDto> findDishesByRestaurantIdSort(Long restaurantId, String[] sort){
        Optional<RestaurantEntity> maybeRestaurant = restaurantRepository.findById(restaurantId);
        if (!maybeRestaurant.isPresent()){
            log.error("Restaurant not found by id = {}", restaurantId);
            throw new RestaurantNotFoundException("Restaurant not found by id = " + restaurantId);
        }
        Sort sortResult = CreateSort.getSort( sort[1], sort[0]);
        List<DishEntity> dishEntities =
                dishRepository.findDishEntitiesByRestaurantEntity(maybeRestaurant.get(), sortResult);
        return dishEntities.stream()
                .map(dishEntity -> dishMapper.dishToDto(dishEntity))
                .collect(Collectors.toList());
    }

    @Override
    public DishDto findDishDtoById(Long id) {
        Optional<DishEntity> maybeDishEntity = dishRepository.findById(id);
        if (!maybeDishEntity.isPresent()){
            log.error("Dish not found by id = {}", id);
            throw new DishNotFoundException("Dish not found by id = " + id);
        }
        return dishMapper.dishToDto(maybeDishEntity.get());
    }

    @Override
    public DishDto addNewDish(Long restaurantId){
        DishDto dishDto = new DishDto();
        RestaurantDto restaurantDto = restaurantService.findRestaurantDtoById(restaurantId);
        dishDto.setRestaurantId(restaurantDto.getId());
        dishDto.setRestaurantName(restaurantDto.getName());  // сэтим имя ресторана, чтобы отображалось на странице
        return dishDto;
    }

    @Override
    @Transactional
    public DishDto saveOrUpdateDish(DishDto dishDto) {
        dishDto.setImageUrl(dishDto.getImage().getOriginalFilename());  // сетим URL для картинки по имени файла
        if (!dishDto.getImage().isEmpty()){
            try {
                FileUploadUtil.saveFile(dishDto.getImage().getOriginalFilename(), dishDto.getImage());
            } catch (IOException e) {
                log.error("Error save file");
                e.printStackTrace();
            }
        }
        DishEntity dishEntity = dishMapper.dishToEntity(dishDto);
        if (dishEntity.getId() == null) {
            DishEntity dishEntitySave = dishRepository.save(dishEntity);
            return dishMapper.dishToDto(dishEntitySave);
        } else {
            DishEntity dishEntityUpdate = dishCustomRepository.updateDish(dishEntity);
            return dishMapper.dishToDto(dishEntityUpdate);
        }
    }

    @Override
    @Transactional
    public void deleteDish(Long id) {
        dishRepository.deleteById(id);

    }
}
