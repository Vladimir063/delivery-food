package com.vladimir.deliveryfood.controller;

import com.vladimir.deliveryfood.dto.RestaurantDto;
import com.vladimir.deliveryfood.services.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
@Slf4j
@Controller
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("restaurant/new")
    public String newRestaurantStep1(Model model){
        log.info("Start create new restaurant");
        RestaurantDto restaurantDto = new RestaurantDto();
        model.addAttribute("restaurant", restaurantDto);
        return "/restaurant/restaurant-form";
    }

    // принимаем запрос на обновление и передаем ДТО на форму обновления
    @GetMapping("/restaurant/{id}/update")
    public String updateRestaurantStep1(@PathVariable Long id, Model model) {
        log.info("Update restaurant with id = {}", id);
        RestaurantDto restaurantDto = restaurantService.findRestaurantDtoById(id);
        model.addAttribute("restaurant", restaurantDto);
        return "/restaurant/restaurant-form";
    }

    @PostMapping("/restaurant/save-or-update")
    public  String saveOrUpdateRestaurant(@Valid @ModelAttribute("restaurant") RestaurantDto restaurantDto,
                                          BindingResult result){
        log.info("Save or update restaurant ");
        if (result.hasErrors()) {
            return "/restaurant/restaurant-form";
        }
        restaurantService.saveOrUpdateRestaurant(restaurantDto);
        return  "redirect:/";
    }

    @GetMapping("/restaurant/{id}/delete")
    public  String deleteRestaurant(@PathVariable Long id){
        log.info("Delete restaurant with id = {}" , id);
        restaurantService.deleteRestaurant(id);
        return  "redirect:/";
    }

}
