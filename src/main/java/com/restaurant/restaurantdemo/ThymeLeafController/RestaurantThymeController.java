package com.restaurant.restaurantdemo.ThymeLeafController;

import com.restaurant.restaurantdemo.model.Restaurant;
import com.restaurant.restaurantdemo.service.LoggerService;
import com.restaurant.restaurantdemo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantThymeController {
    private final RestaurantService restaurantService;
    private final LoggerService logger;

    @Autowired
    public RestaurantThymeController(RestaurantService restaurantService, LoggerService logger) {
        this.restaurantService = restaurantService;
        this.logger = logger;
    }

    @GetMapping("/all")
    public String index(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }
}
