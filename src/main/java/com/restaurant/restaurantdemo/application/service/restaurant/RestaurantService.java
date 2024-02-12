package com.restaurant.restaurantdemo.application.service.restaurant;



import com.restaurant.restaurantdemo.domain.menu.Menu;
import com.restaurant.restaurantdemo.domain.restaurant.Restaurant;
import com.restaurant.restaurantdemo.boundary.output.jpa.menu.MenuRepository;
import com.restaurant.restaurantdemo.boundary.output.jpa.restaurant.RestaurantRepository;
import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // if you use IntelliJ please import our formatter from e.g. customer-api project (/common/Kitopi_Intellij_Formatter.xml) and use auto-formatting on files
@RequiredArgsConstructor
public class RestaurantService {
    @Autowired
    private final RestaurantRepository restaurantRepository;
    @Autowired
    private final MenuRepository menuRepository;


    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
    }


    public Restaurant createRestaurant(Restaurant restaurant) {

        if (restaurantRepository.findByName(restaurant.getName()) != null) {
            throw new IllegalStateException("A restaurant with the name '" + restaurant.getName() + "' already exists.");
        }
        return restaurantRepository.save(restaurant);

    }

    public void updateRestaurant(Long restaurantId, Restaurant restaurantDetails) {


        if (restaurantRepository.findByNameAndIdNot(restaurantDetails.getName(), restaurantId) != null) {
            throw new IllegalStateException("A restaurant with the name '" + restaurantDetails.getName() + "' already exists.");
        }
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));

        existingRestaurant.setName(restaurantDetails.getName());
        existingRestaurant.setAddress(restaurantDetails.getAddress());

        // Save the updated restaurant
        restaurantRepository.save(existingRestaurant);

    }

    @Transactional
    public void linkRestaurantToMenu(Long restaurantId, Long menuId) {

        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with ID: " + menuId));
        existingRestaurant.setMenu(menu);
        // Save the updated restaurant
        restaurantRepository.save(existingRestaurant);

    }


    @Transactional
    public void unlinkRestaurantToMenu(Long restaurantId) {

        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));
        existingRestaurant.setMenu(null);
        // Save the updated restaurant
        restaurantRepository.save(existingRestaurant);

    }


    public void deleteRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new EntityNotFoundException("Restaurant with ID " + restaurantId + " not found");
        }
        restaurantRepository.deleteById(restaurantId);
    }
}
