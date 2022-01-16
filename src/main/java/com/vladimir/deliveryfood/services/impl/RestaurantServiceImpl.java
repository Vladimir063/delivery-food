package com.vladimir.deliveryfood.services.impl;

import com.vladimir.deliveryfood.dto.RestaurantDto;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import com.vladimir.deliveryfood.exception.RestaurantNotFoundException;
import com.vladimir.deliveryfood.mapper.RestaurantMapper;
import com.vladimir.deliveryfood.repository.RestaurantCustomRepository;
import com.vladimir.deliveryfood.repository.RestaurantRepository;
import com.vladimir.deliveryfood.services.RestaurantService;
import com.vladimir.deliveryfood.util.CreateSort;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper  restaurantMapper;
    private  final RestaurantCustomRepository restaurantCustomRepository;

    @Override
    public List<RestaurantDto> findAllDto() {
        List<RestaurantEntity> restaurantEntityAll = restaurantRepository.findAll();
        return restaurantEntityAll.stream()
                .map(restaurant -> restaurantMapper.restaurantToDto(restaurant))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDto> findAllDtoSort(String[] sort) {

        Sort sortResult = CreateSort.getSort(sort[1], sort[0]);

        List<RestaurantEntity> restaurantEntityAll = restaurantRepository.findAll(sortResult);
        return restaurantEntityAll.stream()
                .map(restaurant -> restaurantMapper.restaurantToDto(restaurant))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDto> findByCuisine(String cuisine) {
        List<RestaurantEntity> restaurantEntityAll = restaurantRepository.findByCuisine(cuisine);
        return restaurantEntityAll.stream()
                .map(restaurant -> restaurantMapper.restaurantToDto(restaurant))
                .collect(Collectors.toList());
    }

    // создаем направление сортировки
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @Override
    public RestaurantDto findRestaurantDtoById(Long id){
        Optional<RestaurantEntity> maybeRestaurant = restaurantRepository.findById(id);
        if (!maybeRestaurant.isPresent()){
            log.error("Restaurant not found by id = {}" , id);
            throw new RestaurantNotFoundException("Restaurant not found by id = " + id);
        }
        return  restaurantMapper.restaurantToDto(maybeRestaurant.get());
    }

    @Override
    @Transactional
    @SneakyThrows
    public RestaurantDto saveOrUpdateRestaurant(RestaurantDto restaurantDto) {
//        restaurantDto.setLogoUrl(restaurantDto.getLogo().getOriginalFilename());  // сетим URL для лого
      //  fileService.uploadFile(restaurantDto.getLogo());

//       if (!restaurantDto.getLogo().isEmpty()){   // делаем сохранение файл, только если он существует
//           FileUploadUtil.saveFile(restaurantDto.getLogo().getOriginalFilename(), restaurantDto.getLogo());
//       }

        RestaurantEntity restaurantEntity = restaurantMapper.restaurantToEntity(restaurantDto);
        if (restaurantEntity.getId() == null) {
            RestaurantEntity restaurantEntitySave = restaurantRepository.save(restaurantEntity);

            return restaurantMapper.restaurantToDto(restaurantEntitySave);
        } else {
            RestaurantEntity restaurantEntityUpdate = restaurantCustomRepository.updateRestaurant(restaurantEntity);

            return restaurantMapper.restaurantToDto(restaurantEntityUpdate);
        }
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

}
