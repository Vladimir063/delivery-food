package com.vladimir.deliveryfood.services;

import com.vladimir.deliveryfood.dto.OrderDto;
import com.vladimir.deliveryfood.entity.OrderEntity;
import com.vladimir.deliveryfood.mapper.OrderMapper;
import com.vladimir.deliveryfood.repository.OrderRepository;
import com.vladimir.deliveryfood.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderMapper orderMapper;

    @Test
    void findAllOrders() {
        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setId(1L);
        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(2L);
        ArrayList<OrderEntity> orderEntities = new ArrayList<>(List.of(orderEntity2, orderEntity1));

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setId(1L);
        OrderDto orderDto2 = new OrderDto();
        orderDto2.setId(2L);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        when(orderRepository.findAll(sort)).thenReturn(orderEntities);
        when(orderMapper.orderToDto(orderEntity1)).thenReturn(orderDto1);
        when(orderMapper.orderToDto(orderEntity2)).thenReturn(orderDto2);

        List<OrderDto> allOrders = orderService.findAllOrders(new String[]{"id", "desc"});

        assertThat(allOrders).hasSize(2);
        assertThat(allOrders.get(0).getId()).isEqualTo(orderEntity2.getId());

    }


    @Test
    void findById() {
        // given
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        when(orderMapper.orderToDto(orderEntity)).thenReturn(orderDto);

        // when
        OrderDto orderDtoFind = orderService.findById(1L);

        // then
        assertThat(orderDtoFind.getId()).isEqualTo(orderDto.getId());
        verify(orderRepository, times(1)).findById(anyLong());

    }

    @Test
    void deleteOrder() {
        orderService.deleteOrder(100L);

        verify(orderRepository, times(1)).deleteById(100L);
    }
}