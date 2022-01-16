package com.vladimir.deliveryfood.services.impl;

import com.vladimir.deliveryfood.dto.*;
import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.entity.OrderEntity;
import com.vladimir.deliveryfood.entity.RestaurantEntity;
import com.vladimir.deliveryfood.exception.DishNotFoundException;
import com.vladimir.deliveryfood.exception.OrderNotFoundException;
import com.vladimir.deliveryfood.exception.RestaurantNotFoundException;
import com.vladimir.deliveryfood.mapper.DishMapper;
import com.vladimir.deliveryfood.mapper.OrderMapper;
import com.vladimir.deliveryfood.repository.DishRepository;
import com.vladimir.deliveryfood.repository.OrderCustomRepository;
import com.vladimir.deliveryfood.repository.OrderRepository;
import com.vladimir.deliveryfood.repository.RestaurantRepository;
import com.vladimir.deliveryfood.services.OrderService;
import com.vladimir.deliveryfood.util.CreateSort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderCustomRepository orderCustomRepository;


    @Override
    public List<OrderDto> findAllOrders(String[] sort){
        Sort sortResult = CreateSort.getSort(sort[1], sort[0]);
        List<OrderEntity> orderEntityList = orderRepository.findAll(sortResult);
        return orderEntityList.stream()
                .map(orderEntity -> orderMapper.orderToDto(orderEntity))
                .collect(toList());
    }

    // сохранение заказа шаг 1
    @Override
    public OrderDto saveOrderStep1(OrderDto orderDto) {
        return orderDto;
    }

    // сохранение заказа шаг 2
    @Override
    @Transactional
    public OrderDto saveOrUpdateOrderStep2(OrderDto orderDto) {
        OrderEntity orderEntity = orderMapper.orderToEntity(orderDto);  // получили с фронтенда c заполненными полями

        Optional<RestaurantEntity> maybeRestaurant = restaurantRepository.findById(orderDto.getRestaurantId());
        if (!maybeRestaurant.isPresent()){
            log.error("Restaurant not found by id = {}", orderDto.getRestaurantId());
            throw new RestaurantNotFoundException("Restaurant not found by id = " + orderDto.getRestaurantId());
        }
        orderEntity.setRestaurantEntity(maybeRestaurant.get());    // сеттим в энтити ресторан

        Optional<DishEntity> maybeDish = dishRepository.findByName(orderDto.getSelectDish());
        if (!maybeDish.isPresent()) {
            log.error("Dish not found by id = {}", orderDto.getSelectDish());
            throw new DishNotFoundException("Dish not found by id = " + orderDto.getSelectDish());
        }
        orderEntity.setDishEntity(maybeDish.get());  // сеттим в энити блюдо

        orderEntity.setTotalCost(orderEntity.getDishEntity().getPrice() * orderEntity.getAmount()
                + orderEntity.getRestaurantEntity().getCostOfDelivery());  // вычисление общей стоимости заказа

        orderEntity.setTimeCreateOrder(LocalDateTime.now());
        if (orderDto.getId() == null) {
            OrderEntity saveOrderEntity = orderRepository.save(orderEntity); // сохранили в базу
            return orderMapper.orderToDto(saveOrderEntity);
        } else {
            OrderEntity orderEntityUpdate = orderCustomRepository.updateOrder(orderEntity);  // обновили
            return orderMapper.orderToDto(orderEntityUpdate);
        }
    }

    @Override
    public OrderDto findById(Long id){
        Optional<OrderEntity> maybeOrder = orderRepository.findById(id);
        if (!maybeOrder.isPresent()){
            log.error("Order not found by id = {}" , id);
            throw new OrderNotFoundException("Order not found by id = " + id);
        }
        return orderMapper.orderToDto(maybeOrder.get());
    }


    @Override
    @Transactional
    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

}
