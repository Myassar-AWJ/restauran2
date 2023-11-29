package com.restaurant.restaurantdemo.ThymeLeafController;

import com.restaurant.restaurantdemo.model.Restaurant;
import com.restaurant.restaurantdemo.service.LoggerService;
import com.restaurant.restaurantdemo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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
        return "restaurants-all";
    }

    @GetMapping("/add-restaurant-page")
    public String addRestaurantPage(Restaurant restaurant) {
        return "restaurants-add";
    }



    @PostMapping("/add-restaurant")
    public String createRestaurant(@Valid Restaurant restaurant, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Add attributes and return the form view
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.restaurant", bindingResult);
            redirectAttributes.addFlashAttribute("restaurant", restaurant);
            return "restaurants-add"; // Replace with your form URL
        }

        try {
            var newRestaurant = restaurantService.createRestaurant(restaurant);
            return "redirect:/restaurants/all"; // Redirect after successful creation
        } catch (Exception e) {
            logger.error("Error while creating new restaurant", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating restaurant");
            return "redirect:/restaurants/add-restaurant-page"; // Replace with your form URL
        }
    }


    @GetMapping("/restaurants/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);

        if (!restaurant.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Restaurant not found.");
            return "redirect:/restaurants/all";
        }

        model.addAttribute("restaurant", restaurant.get());
        return "restaurants-edit"; // Name of the Thymeleaf template for editing restaurants
    }

    @GetMapping("get-restaurants")
    public ModelAndView createUserView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("restaurants-page");
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        mav.addObject("restaurants", restaurants);
        mav.addObject("allProfiles", "data");
        return mav;
    }

    @GetMapping("/index")
    public String index2(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "index";
    }
}
