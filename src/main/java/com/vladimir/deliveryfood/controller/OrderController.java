package com.vladimir.deliveryfood.controller;

import com.vladimir.deliveryfood.dto.*;
import com.vladimir.deliveryfood.services.OrderService;
import com.vladimir.deliveryfood.services.RestaurantService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
@Slf4j
@Controller
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final RestaurantService restaurantService;


    @GetMapping("/order")
    public String findAllOrders(@RequestParam(defaultValue = "id,asc") String[] sort , Model model) {
        log.info("Get all orders ");
        List<OrderDto> orderDtoList = orderService.findAllOrders(sort);
        model.addAttribute("orders", orderDtoList);
        return "/order/show";
    }

    @GetMapping("order/new")
    public String newOrder(Model model){
        log.info("Start create order");
        OrderDto orderDto = new OrderDto();
        List<RestaurantDto> restaurantDtoList = restaurantService.findAllDto();  // ищем все рестораны для показа в  выпадающем меню
        model.addAttribute("order", orderDto);
        model.addAttribute("restaurants", restaurantDtoList);
        return "/order/order-form-step1";
    }

    // сохранение заказа шаг 1
    @PostMapping("/order/save-or-update-order-step1")
    public String saveOrUpdateOrderStep1(@ModelAttribute("order") OrderDto orderDto, Model model, HttpSession session) {
        log.info("Save or update order step1");
        // находим ресторан, для отображения его блюд
        RestaurantDto restaurantDto = restaurantService.findRestaurantDtoById(orderDto.getRestaurantId());
        orderDto.setRestaurantName(restaurantDto.getName()); // сетим имя ресторана в заказ
        session.setAttribute("listDishesByRestaurant", restaurantDto.getDishesDto()); //
        model.addAttribute("order", orderDto);
        return "order/order-form-step2";
    }

    // сохранение заказа шаг 2
    @PostMapping("/order/save-or-update-order-step2")
    public String saveOrUpdateOrderStep2(@Valid @ModelAttribute("order") OrderDto orderDto, BindingResult result) {
        log.info("Save or update order step2");
        if (result.hasErrors()){
            return "order/order-form-step2";
        }
        orderService.saveOrUpdateOrderStep2(orderDto);
        return "redirect:/order";
    }

    // обновление заказа , начинаем с шага 2 сразу
    @GetMapping("/order/{id}/update")
    public String updateOrderStep1(@PathVariable("id") Long id, Model model, HttpSession session){
        log.info("Update order step1. Order id = {}" , id );
        OrderDto orderDto = orderService.findById(id);
        RestaurantDto restaurantDto = restaurantService.findRestaurantDtoById(orderDto.getRestaurantId());
        orderDto.setRestaurantName(restaurantDto.getName()); // сетим имя ресторана в заказ
        session.setAttribute("listDishesByRestaurant", restaurantDto.getDishesDto()); //
        model.addAttribute("order", orderDto);
        return "order/order-form-step2";
    }

    @GetMapping("/order/{id}/delete")
    public String deleteOrder(@PathVariable("id") Long id){
        log.info("Delete order with id = {}", id);
        orderService.deleteOrder(id);
        return "redirect:/order";
    }

}
