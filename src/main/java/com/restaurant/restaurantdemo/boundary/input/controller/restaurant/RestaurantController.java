package com.restaurant.restaurantdemo.boundary.input.controller.restaurant;

import com.restaurant.restaurantdemo.application.service.ResponseWithData;
import com.restaurant.restaurantdemo.application.service.LoggerService;
import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


import com.restaurant.restaurantdemo.domain.restaurant.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.restaurant.restaurantdemo.application.service.restaurant.RestaurantService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
@RestController("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    public RestaurantController(RestaurantService restaurantService ) {
        this.restaurantService = restaurantService;

    }

    @GetMapping
    public ResponseEntity<ResponseWithData<List<Restaurant>>> getAllRestaurants() {
        try {

            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            ResponseWithData<List<Restaurant>> response = new ResponseWithData<>("Success", restaurants);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the exception for debugging purposes

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

            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>("Restaurant not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    //    @PostMapping
    @PostMapping()
    public ResponseEntity<ResponseWithData<Restaurant>> createRestaurant(@RequestBody @Valid  Restaurant restaurant , BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // Collect error messages and return as a response
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.toList());

                ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>("Failed", errors);
                return ResponseEntity.badRequest().body(errorResponse);
            }
            var newRestaurant= restaurantService.createRestaurant(restaurant);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", newRestaurant);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the exception

            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }

    }

    @PutMapping("/{RestaurantId}")
    public ResponseEntity<ResponseWithData<Restaurant>> updateRestaurant(@PathVariable Long RestaurantId, @RequestBody @Valid Restaurant RestaurantDetails) {
        try{
            var restaurant= restaurantService.updateRestaurant(RestaurantId, RestaurantDetails);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            // Log the exception

            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PutMapping("/{RestaurantId}/menu/{MenuId}")
    public ResponseEntity<ResponseWithData<Restaurant>> linkRestaurantToMenu(@PathVariable Long RestaurantId,@PathVariable Long MenuId) {
        try{
            var restaurant= restaurantService.linkRestaurantToMenu(RestaurantId,MenuId);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            // Log the exception

            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{RestaurantId}/menu/clear")
    public ResponseEntity<ResponseWithData<Restaurant>> unlinkRestaurantToMenu(@PathVariable Long RestaurantId) {
        try{
            var restaurant= restaurantService.unlinkRestaurantToMenu(RestaurantId);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            // Log the exception

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

            ResponseWithData<Void> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


}
