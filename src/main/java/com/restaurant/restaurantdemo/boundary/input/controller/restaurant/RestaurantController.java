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


    @GetMapping("/{restaurantId}")
    public RestaurantDTO getRestaurantById(@PathVariable Long restaurantId) {

        var restaurant = restaurantService.getRestaurantById(restaurantId);
        return modelMapper.map(restaurant, RestaurantDTO.class);


    }

    //    @PostMapping , BindingResult bindingResult
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createRestaurant(@RequestBody @Valid RestaurantDTO restaurantDto) {

        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant newRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(newRestaurant.getId());

    }


    @PutMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  updateRestaurant(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantDTO restaurantDto) {
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant updatedrestaurant = restaurantService.updateRestaurant(restaurantId, restaurant);
    }

    @PutMapping("/{restaurantId}/menu/{menuId}")
    public ResponseEntity<RestaurantDTO> linkRestaurantToMenu(@PathVariable Long restaurantId, @PathVariable Long menuId) {

        var restaurant = restaurantService.linkRestaurantToMenu(restaurantId, menuId);
        RestaurantDTO restaurantDto = modelMapper.map(restaurant, RestaurantDTO.class);
        return ResponseEntity.ok(restaurantDto);

    }

    @PutMapping("/{restaurantId}/menu/clear")
    public ResponseEntity<ResponseWithData<Restaurant>> unlinkRestaurantToMenu(@PathVariable Long restaurantId) {
        try {
            var restaurant = restaurantService.unlinkRestaurantToMenu(restaurantId);
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
