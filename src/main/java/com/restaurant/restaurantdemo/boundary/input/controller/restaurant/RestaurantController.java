package com.restaurant.restaurantdemo.boundary.input.controller.restaurant;

import com.restaurant.restaurantdemo.application.dto.restaurant.RestaurantDTO;
import com.restaurant.restaurantdemo.application.service.ResponseWithData;
import com.restaurant.restaurantdemo.application.service.LoggerService;
import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


import com.restaurant.restaurantdemo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
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
@RestController
//@RestController("/api/restaurants")
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    @Autowired
    private final RestaurantService restaurantService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class))
                .collect(Collectors.toList());

    }


    @GetMapping("/{RestaurantId}")
    public RestaurantDTO getRestaurantById(@PathVariable Long RestaurantId) {

        var restaurant = restaurantService.getRestaurantById(RestaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found")); // Customize the exception type
        return modelMapper.map(restaurant, RestaurantDTO.class);


    }

    //    @PostMapping
    @PostMapping()
    public RestaurantDTO createRestaurant(@RequestBody @Valid RestaurantDTO restaurantDto, BindingResult bindingResult) {

        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant newRestaurant = restaurantService.createRestaurant(restaurant);
        return modelMapper.map(newRestaurant, RestaurantDTO.class);

    }
//    @PostMapping()
//    public ResponseEntity<?> createRestaurant(@RequestBody @Valid RestaurantDTO restaurantDto, BindingResult bindingResult) {
////        if (bindingResult.hasErrors()) {
////            List<String> errors = bindingResult.getFieldErrors().stream()
////                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
////                    .collect(Collectors.toList());
////            return ResponseEntity.badRequest().body(new ResponseWithData<>("Failed", errors));
////        }
//        Restaurant newRestaurant = restaurantService.createRestaurant(modelMapper.map(restaurantDto, Restaurant.class));
//        return ResponseEntity.ok(modelMapper.map(newRestaurant, RestaurantDTO.class));
//    }

    @PutMapping("/{RestaurantId}")
    public ResponseEntity<ResponseWithData<Restaurant>> updateRestaurant(@PathVariable Long RestaurantId, @RequestBody @Valid Restaurant RestaurantDetails) {
        try {
            var restaurant = restaurantService.updateRestaurant(RestaurantId, RestaurantDetails);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception

            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{RestaurantId}/menu/{MenuId}")
    public ResponseEntity<ResponseWithData<Restaurant>> linkRestaurantToMenu(@PathVariable Long RestaurantId, @PathVariable Long MenuId) {
        try {
            var restaurant = restaurantService.linkRestaurantToMenu(RestaurantId, MenuId);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception

            ResponseWithData<Restaurant> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{RestaurantId}/menu/clear")
    public ResponseEntity<ResponseWithData<Restaurant>> unlinkRestaurantToMenu(@PathVariable Long RestaurantId) {
        try {
            var restaurant = restaurantService.unlinkRestaurantToMenu(RestaurantId);
            ResponseWithData<Restaurant> response = new ResponseWithData<>("Success", restaurant);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
        } catch (Exception e) {

            ResponseWithData<Void> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


}
