package com.vladimir.deliveryfood.controller;

import com.vladimir.deliveryfood.dto.DishDto;
import com.vladimir.deliveryfood.dto.RestaurantDto;
import com.vladimir.deliveryfood.services.DishService;
import com.vladimir.deliveryfood.services.RestaurantService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@Controller
@AllArgsConstructor
public class DishController {

    private final RestaurantService restaurantService;
    private final DishService dishService;

    @GetMapping("/restaurant/{restaurantId}/dish")
    public String findDishesByRestaurant(@PathVariable Long restaurantId, Model model ){
        log.info("Getting dishes by restaurant id = {}", restaurantId);
        List<DishDto> dishes = dishService.findDishesByRestaurantId(restaurantId);
        RestaurantDto restaurantDto = restaurantService.findRestaurantDtoById(restaurantId);
        model.addAttribute("dishes", dishes);
        model.addAttribute("restaurant", restaurantDto);
        return "restaurant/dish/show";
    }

    // для показа блюд в отсортированном виде
    @PostMapping({"/restaurant/sorted-dish"})
    public String findDishesByRestaurant(@RequestParam(defaultValue = "id,asc") String[] sort ,
                                         @RequestParam Long restaurantId, Model model ){
        log.info("Getting sorted dishes by restaurant id = {}", restaurantId);
        List<DishDto> dishes = dishService.findDishesByRestaurantIdSort(restaurantId , sort);
        RestaurantDto restaurantDto = restaurantService.findRestaurantDtoById(restaurantId);
        model.addAttribute("dishes", dishes);
        model.addAttribute("restaurant", restaurantDto);
        return "restaurant/dish/show";
    }

    @PostMapping("restaurant/dish/new")
    public String newDish(@RequestParam("restaurantId") Long restaurantId, Model model){
        log.info("Start create new dish");
        DishDto dishDto = dishService.addNewDish(restaurantId);
        model.addAttribute("dish", dishDto);
        return "restaurant/dish/dish-form";
    }

    @GetMapping("/dish/{id}/update")
    public String upddateDishStep1(@PathVariable("id") Long id, Model model){
        log.info("Start  update dish with id = {}" , id);
        DishDto dishDto = dishService.findDishDtoById(id);
        model.addAttribute("dish", dishDto);
        return "restaurant/dish/dish-form";
    }


    @PostMapping("/restaurant/dish/save-or-update")
    public  String saveOrUpdateDish(@Valid @ModelAttribute("dish") DishDto dishDto, BindingResult result){
        log.info("create or update dish  with name  = {} " , dishDto.getName());
        if (result.hasErrors()) {
            return "restaurant/dish/dish-form";
        }
        dishService.saveOrUpdateDish(dishDto);
        return  "redirect:/restaurant/" + dishDto.getRestaurantId() + "/dish";
    }

    @GetMapping("/dish/{id}/delete")
    public  String deleteDish(@PathVariable("id") Long id){
        log.info("Delete dish wirh id = {}" , id);
        Long restaurantId = dishService.findDishDtoById(id).getRestaurantId(); // // возвращаем id ресторана , чтобы при удалении перейти в меню ресторана
        dishService.deleteDish(id);
        return  "redirect:/restaurant/" + restaurantId + "/dish";
    }
}
