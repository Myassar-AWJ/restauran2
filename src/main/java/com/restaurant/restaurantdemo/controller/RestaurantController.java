package com.restaurant.restaurantdemo.controller;

import com.restaurant.restaurantdemo.model.Product;
import com.restaurant.restaurantdemo.model.ResponseWithData;
import com.restaurant.restaurantdemo.service.LoggerService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.restaurant.restaurantdemo.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.restaurant.restaurantdemo.service.RestaurantService;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final LoggerService logger;

    @Autowired
    public RestaurantController(RestaurantService restaurantService,LoggerService logger) {
        this.restaurantService = restaurantService;
        this.logger=logger;
    }

    @GetMapping
    public ResponseEntity<ResponseWithData<List<Restaurant>>> getAllRestaurants() {
        try {

            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            ResponseWithData<List<Restaurant>> response = new ResponseWithData<>("Success", restaurants);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.error("An error occurred while processing the request", e.getMessage());
            ResponseWithData<List<Restaurant>> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }


    @GetMapping("/{RestaurantId}")
    public ResponseEntity<ResponseWithData<Restaurant>> getRestaurantById(@PathVariable Long RestaurantId) {
        try {
            var restaurant = restaurantService.getRestaurantById(RestaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found")); // Customize the exception type

            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle the specific exception for "Restaurant not found"
            logger.error("Restaurant not found", e.getMessage());
            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>("Restaurant not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    //    @PostMapping
    @PostMapping()
    public ResponseEntity<ResponseWithData<Restaurant>> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            var newRestaurant= restaurantService.createRestaurant(restaurant);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", newRestaurant);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the exception
            logger.error("Error while  creating new restaurant in cont", e.getMessage());
            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }

    }

    @PutMapping("/{RestaurantId}")
    public ResponseEntity<ResponseWithData<Restaurant>> updateRestaurant(@PathVariable Long RestaurantId, @RequestBody Restaurant RestaurantDetails) {
        try{
            var restaurant= restaurantService.updateRestaurant(RestaurantId, RestaurantDetails);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            // Log the exception
            logger.error("Error while updating restaurant in cont", e.getMessage());
            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{RestaurantId}")
    public ResponseEntity<ResponseWithData<Void>> deleteRestaurant(@PathVariable Long RestaurantId) {
        try {
            restaurantService.deleteRestaurant(RestaurantId);
            ResponseWithData<Void> response = new ResponseWithData<>("Success");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error while  deleting a  restaurant in cont", e.getMessage());
            ResponseWithData<Void> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


}
