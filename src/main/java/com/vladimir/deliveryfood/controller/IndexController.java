package com.vladimir.deliveryfood.controller;

import com.vladimir.deliveryfood.dto.RestaurantDto;
import com.vladimir.deliveryfood.entity.enums.Cuisine;

import com.vladimir.deliveryfood.services.RestaurantService;
import com.vladimir.deliveryfood.services.impl.RestaurantServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;
import java.util.List;
@Slf4j
@Controller
@AllArgsConstructor
public class IndexController {

    private final RestaurantService restaurantService;

    @GetMapping({"", "/", "/index", "/sorted-restaurants"})
    public String getIndexPage(@RequestParam(defaultValue = "id,asc") String[] sort , Model model, HttpSession session) {
        log.info("Get list restaurants");
        session.setAttribute("cuisinesArray", Cuisine.values()); // добавляем в сессию массив типов ресторанов
        List<RestaurantDto> restaurantDtoList = restaurantService.findAllDtoSort(sort);
        model.addAttribute("restaurants", restaurantDtoList);
        return "index";
    }

    @GetMapping("/restaurants-by-cuisine")
    public String getRestaurantsByCuisine(@RequestParam String cuisine , Model model) {
        log.info("Get list restaurants by cuisine = {}" , cuisine);
        List<RestaurantDto> restaurantDtoList = restaurantService.findByCuisine(cuisine);
        model.addAttribute("restaurants", restaurantDtoList);
        return "index";
    }

}
