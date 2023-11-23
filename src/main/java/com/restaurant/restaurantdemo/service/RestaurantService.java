package com.restaurant.restaurantdemo.service;


import com.restaurant.restaurantdemo.model.Restaurant;
import com.restaurant.restaurantdemo.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final LoggerService logger;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository,LoggerService logger) {
        this.restaurantRepository = restaurantRepository;
        this.logger=logger;
    }


    public List<Restaurant> getAllRestaurants() {
        try {
            return restaurantRepository.findAll();
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while retrieving all restaurants", e.getMessage());
            throw new RuntimeException("Error while retrieving all restaurants", e);
        }

    }

    public Optional<Restaurant> getRestaurantById(Long RestaurantId) {
        try {
            return restaurantRepository.findById(RestaurantId);
        }catch (Exception e) {
            // Log the exception
            logger.error("Error while retrieving restaurant by id", e.getMessage());
            throw new RuntimeException("Error while retrieving  restaurant by id", e);
        }
    }

    public Restaurant createRestaurant(Restaurant Restaurant) {
        try {
            return restaurantRepository.save(Restaurant);
        }catch (Exception e) {
            // Log the exception
            logger.error("Error while creating restaurant", e);
            throw new RuntimeException("Error while creating  restaurant", e.getCause());
        }
    }

    public Restaurant updateRestaurant(Long restaurantId, Restaurant restaurantDetails) {
        try {
            Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));

//            existingRestaurant.setName(restaurantDetails.getName());
//            existingRestaurant.setAddress(restaurantDetails.getAddress());

            // Save the updated restaurant
            return restaurantRepository.save(existingRestaurant);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while updating restaurant", e.getMessage());
            throw new RuntimeException("Error while updating restaurant", e.getCause());
        }
    }

    public void deleteRestaurant(Long RestaurantId) {
        try {
            restaurantRepository.deleteById(RestaurantId);
        }catch (Exception e) {
            // Log the exception
            logger.error("Error while deleting restaurant", e.getMessage());
            throw new RuntimeException("Error while deleting  restaurant", e);
        }
    }
}
